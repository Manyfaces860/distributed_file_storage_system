package com.dfss.distributed_file_storage_system.service;

import com.dfss.distributed_file_storage_system.entity.File;
import com.dfss.distributed_file_storage_system.entity.db1.FileMetadata;
import com.dfss.distributed_file_storage_system.repository.db1.MetadataRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;


@Service
@Slf4j
public class StorageService {

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Data
    public static class HttpObject {
        private Long id;
        private Long index;
        private String base64Data;
    }

    @Value("${api.post.store.node1}")
    private String postApiNode1;

    @Value("${api.post.store.node2}")
    private String postApiNode2;

    @Value("${api.get.get.node1}")
    private String getApiNode1;

    @Value("${api.get.get.node2}")
    private String getApiNode2;

    @Transactional
    public void save(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        int fileSize = fileBytes.length;
        int blocks = fileSize / 8;
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFileSize((long) fileBytes.length);
        fileMetadata.setFileType(file.getOriginalFilename().split("\\.")[1]);
        fileMetadata.setNodes(Stream.iterate(0, x -> x + 1).limit(2).toList());
        fileMetadata.setTotalChunks((long) 8);

        FileMetadata savedMetadata = metadataRepository.save(fileMetadata);

        int startIndex = 0;
        int endIndex = startIndex + blocks;
        HttpObject[] httpObjects = new HttpObject[8];
        for (int i = 0; i < 8; i++) {
            byte[] bytes = Arrays.copyOfRange(fileBytes, startIndex, endIndex);
            httpObjects[i] = new HttpObject();
            httpObjects[i].setId(savedMetadata.getId());
            httpObjects[i].setIndex((long) i);
            httpObjects[i].setBase64Data(Base64.getEncoder().encodeToString(bytes));
            startIndex = endIndex;
            endIndex = endIndex + blocks;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        HttpEntity<HttpObject[]> node1 = new HttpEntity<>(Arrays.copyOfRange(httpObjects, 0, httpObjects.length/2));
        HttpEntity<HttpObject[]> node2 = new HttpEntity<>(Arrays.copyOfRange(httpObjects, httpObjects.length/2, httpObjects.length));

        List<Callable<Void>> tasks = new ArrayList<>();
        tasks.add(() -> {
            ResponseEntity<Void> response = restTemplate.exchange(postApiNode1, HttpMethod.POST, node1, Void.class);
            return null;
        });
        tasks.add(() -> {
            ResponseEntity<Void> response = restTemplate.exchange(postApiNode2, HttpMethod.POST, node2, Void.class);
            return null;
        });

        try {
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            executorService.shutdown();
            executorService.awaitTermination(40, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
           log.error("Thread Interrupted while executing task: called store file");
        } finally {
           executorService.shutdownNow();
        }

    }

    public File getFile(String fileName) throws Exception {

        FileMetadata fileMetadata =  metadataRepository.getFileMetadataByFileName(fileName).get(0);
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        List<Callable<List<HttpObject>>> tasks = new ArrayList<>();
        tasks.add(() -> {
            ResponseEntity<List<HttpObject>> response = restTemplate.exchange(getApiNode1+fileMetadata.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<HttpObject>>() {});
            return response.getBody();
        });
        tasks.add(() -> {
            ResponseEntity<List<HttpObject>> response = restTemplate.exchange(getApiNode2+fileMetadata.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<HttpObject>>() {});
            return response.getBody();
        });

        // why should i use Math.toIntExact to cast long to int
        byte[] fileBytes = new byte[Math.toIntExact(fileMetadata.getFileSize())];
        List<HttpObject> objects = new ArrayList<>();
        try {
            List<Future<List<HttpObject>>> futures = executorService.invokeAll(tasks);
            System.out.println(futures.size());
            // what is this final thing in runnable
            futures.forEach(future -> {
                try {
                    List<HttpObject> httpObjects = future.get();
                    httpObjects.forEach(httpObject -> {
                        objects.add(httpObject);
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch(InterruptedException e) {
            log.error("Thread Interrupted while executing task: called get file");
            throw new Exception("Thread Interrupted while executing task: called get file");
        } finally {
            executorService.shutdownNow();
        }

        objects.sort(Comparator.comparingLong(HttpObject::getIndex));
        final int[] index = {0};

        objects.forEach(httpObject -> {
            for (byte b : Base64.getDecoder().decode(httpObject.getBase64Data())) {
                fileBytes[index[0]] = b;
                index[0]++;
            }
        });

        File file = new File();
        file.setFileName(fileMetadata.getFileName());
        file.setFileType(fileMetadata.getFileType());
        file.setFileSize(fileMetadata.getFileSize());
        file.setContent(Base64.getEncoder().encodeToString(fileBytes));

        return file;
    }

    public void deleteAll() {
        metadataRepository.deleteAll();
    }
}
