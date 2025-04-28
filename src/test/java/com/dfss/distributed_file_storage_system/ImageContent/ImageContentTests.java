package com.dfss.distributed_file_storage_system.ImageContent;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

//@SpringBootTest
public class ImageContentTests {

//    private RestTemplate restTemplate = new RestTemplate();
//
//    private static final String api = "http://localhost:8081/blob/get-file?fileId=45";
//
//    @Data
//    public static class ResponseObject {
//        private Long id;
//        private Long index;
//        private String base64Data;
//    }
//
//    @Test
//    void callService() throws IOException {
//        ResponseEntity<List<ResponseObject>> response = restTemplate.exchange(api, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseObject>>() {});
//
//        response.getBody().forEach(responseObject -> {
//            try {
//                Files.write(Path.of("myphoto.png"), Base64.getDecoder().decode(responseObject.getBase64Data()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    @Test
//    void storeService() throws IOException {
//        byte[] fileBytes = Files.readAllBytes(Path.of("C:/Users/agbli/Pictures/Saved Pictures/me2.jpg"));
//        ResponseObject ro = new ResponseObject();
//        ro.setId(45l);
//        ro.setIndex(2l);
//        ro.setBase64Data(Base64.getEncoder().encodeToString(fileBytes));
//        HttpEntity<List<ResponseObject>> request = new HttpEntity<>(List.of(ro));
//        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8081/blob/store-file", HttpMethod.POST, request, Void.class);
//
//    }

//    @Test
//    void compare() throws IOException {
//        byte[] i1 = Files.readAllBytes(Path.of("emotion_detec_snapshot_graph.png"));
//        byte[] i2 = Files.readAllBytes(Path.of("myphoto.png"));
//
//        System.out.println(Objects.equals(Base64.getEncoder().encodeToString(i1), Base64.getEncoder().encodeToString(i2)));
//    }
}
