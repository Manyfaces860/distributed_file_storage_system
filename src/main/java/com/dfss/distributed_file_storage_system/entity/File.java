package com.dfss.distributed_file_storage_system.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
public class File {
    private String fileName;

    private String fileType;

    private Long fileSize;

    private String content;
}
