package com.services.services.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirectoryInitializer {

    private static final String IMAGE_FOLDER = "uploads/";

    @PostConstruct
    public void init() {
        File dir = new File(IMAGE_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
