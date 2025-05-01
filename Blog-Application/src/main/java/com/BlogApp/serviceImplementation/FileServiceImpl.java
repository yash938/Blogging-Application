package com.BlogApp.serviceImplementation;

import com.BlogApp.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String UploadFile(MultipartFile multipartFile, String path) {

        String originalFilename = multipartFile.getOriginalFilename();
        log.info("Original filename: {}", originalFilename);

        // Extract extension from the original filename
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info("File extension: {}", extension);

        // Generate a unique filename
        String fileName = UUID.randomUUID().toString();
        String fileNameWithExtension = fileName + extension;
        String fullPathFileName = path +fileNameWithExtension;

        // Validate allowed file types
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            // Create the directory if it doesn't exist
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            // Copy the file to the target location
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(fullPathFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new   RuntimeException("Failed to upload file: " + e.getMessage());
            }

        } else {
            throw new   RuntimeException("File with extension " + extension + " is not allowed");
        }

        return fileNameWithExtension; // Return the unique filename
    }

    @Override
    public InputStream getResource(String path, String name) {
        try {
            String fileInput = path + File.separator + name;
            InputStream inputStream = new FileInputStream(fileInput);
            return inputStream;
        }catch (Exception e){
            throw new RuntimeException("FIle is not found");
        }
    }
}
