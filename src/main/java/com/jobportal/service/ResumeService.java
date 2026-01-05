package com.jobportal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ResumeService {

    private static final String RESUME_DIR = "D:/jobportal/resumes/";

    public String saveResume(String base64, String originalFileName)
            throws IOException {

        if (base64 == null || originalFileName == null) {
            return null;
        }

        //  Only PDF allowed
        if (!originalFileName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF resumes allowed");
        }

        byte[] fileBytes = Base64.getDecoder().decode(base64);

        //  Max size 2MB
        if (fileBytes.length > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("Resume size exceeds 2MB");
        }

        Files.createDirectories(Paths.get(RESUME_DIR));

        String fileName =
            System.currentTimeMillis() + "_" + originalFileName;

        Path filePath = Paths.get(RESUME_DIR, fileName);
        Files.write(filePath, fileBytes);

        return filePath.toString(); //  store only path in DB
    }
}

