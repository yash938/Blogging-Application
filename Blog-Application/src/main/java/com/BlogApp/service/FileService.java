package com.BlogApp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String UploadFile(MultipartFile multipartFile,String path);
    InputStream getResource(String path,String name);

}
