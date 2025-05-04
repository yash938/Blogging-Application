package com.BlogApp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

public interface ImageService {
    public String upload(MultipartFile image);
}
