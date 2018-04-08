package com.sdinga.image.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:imagecloud.properties")
@ConfigurationProperties(prefix = "image")
public class ImageCloudConfig {

    @Value("${image.cloud.config}")
    private String imageCloud;

    public String getImageCloud() {
        return imageCloud;
    }

    public void setImageCloud(String imageCloud) {
        this.imageCloud = imageCloud;
    }
}
