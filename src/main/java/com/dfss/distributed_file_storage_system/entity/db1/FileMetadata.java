package com.dfss.distributed_file_storage_system.entity.db1;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="file_metadata")
@Data
public class FileMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String fileName;

    @Column(length=100, nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @ElementCollection
    @CollectionTable(
            name="nodes_table",
            joinColumns = @JoinColumn(name="entity_id")
    )
    @Column(name="node_value", nullable = false)
    private List<Integer> nodes = new ArrayList<>();

    @Column(nullable = false)
    private Long totalChunks;
}
