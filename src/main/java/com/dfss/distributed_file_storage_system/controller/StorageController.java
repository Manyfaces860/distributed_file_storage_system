package com.dfss.distributed_file_storage_system.controller;

import com.dfss.distributed_file_storage_system.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<?> setFile(@RequestParam("file") MultipartFile file) throws IOException {
        storageService.save(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) throws Exception {
        return new ResponseEntity(storageService.getFile(fileName), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() {
        storageService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
