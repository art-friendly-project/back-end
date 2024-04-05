package com.artfriendly.artfriendly.domain.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String fileUpLoad(MultipartFile multipartFile) throws IOException;
    String getImageUrl(String fileName);
    void deleteImageByFileName(String fileName);
}
