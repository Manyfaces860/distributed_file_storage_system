//package com.dfss.distributed_file_storage_system.entity.db2;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.io.Serializable;
//
//@Entity
//@Table(name="chunk")
//@Data
//public class Chunk2 implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(length = 10000000, nullable = false)
//    private String content;
//
//    @Column(nullable = false)
//    private Integer part = 2;
//}
