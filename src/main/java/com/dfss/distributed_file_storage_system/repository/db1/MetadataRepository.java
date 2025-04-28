package com.dfss.distributed_file_storage_system.repository.db1;

import com.dfss.distributed_file_storage_system.entity.db1.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface MetadataRepository extends JpaRepository<FileMetadata, Long> {

    FileMetadata getFileMetadataById(Integer id);
    List<FileMetadata> getFileMetadataByFileName(String fileName);
}
