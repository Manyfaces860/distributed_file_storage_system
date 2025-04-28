package com.dfss.distributed_file_storage_system.utility;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;

import java.io.IOException;
import java.io.InputStream;

public class DetectContentType {

    public String detectContentType(InputStream stream) throws IOException {
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();
        return detector.detect(stream, metadata).toString();
    }
}
