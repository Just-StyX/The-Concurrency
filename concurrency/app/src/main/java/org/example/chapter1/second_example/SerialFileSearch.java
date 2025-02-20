package org.example.chapter1.second_example;

import java.io.File;

public class SerialFileSearch {
    public static void searchFiles(File file, String filename, Result result) {
        File[] contents = file.listFiles();
        if (contents == null) return;
        for (File content: contents) {
            if (content.isDirectory()) {
                searchFiles(content, filename, result);
            } else  {
                if (content.getName().equals(filename)){
                    result = new Result(true, content.getAbsolutePath());
                    System.out.printf("Serial Search: Path: %s%n", result.path());
                    return;
                }
            }
            if (result.found()) return;
        }
    }
}
