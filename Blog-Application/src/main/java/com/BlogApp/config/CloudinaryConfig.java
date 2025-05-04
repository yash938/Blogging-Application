package com.BlogApp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dby9oywcg",
                "api_key", "421533583768444",
                "api_secret", "GxjRznmhnsDwkNRP_GrSNB2eW-0"
        );
        return new Cloudinary(config);
    }
}
