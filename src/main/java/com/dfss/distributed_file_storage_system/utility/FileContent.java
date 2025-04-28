package com.dfss.distributed_file_storage_system.utility;

import java.io.*;

public class FileContent {

    public static String getFileContent(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder content = new StringBuilder();
        String[] result = {"", ""};
        int curChar;

        while ((curChar = reader.read()) != -1) {
            content.append((char) curChar);
        }
        reader.close();

        return content.toString();
    }

    public static void getImageContent(String path) {

    }

}
