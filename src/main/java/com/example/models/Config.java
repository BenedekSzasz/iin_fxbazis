package com.example.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config extends Properties{
    private final String filename = "config.properties";

    public Config() {
        init();
    }

    private void init() {
        try {
            tryInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void tryInit() throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        load(inputStream);
    }
}
