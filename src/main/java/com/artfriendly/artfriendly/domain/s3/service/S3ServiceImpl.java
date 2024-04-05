package com.artfriendly.artfriendly.domain.s3.service;

import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.GenerateFileName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    @Value("${file.path.images}")
    private String imagePath;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Override
    public String fileUpLoad(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }

        String fileName = getFileName(multipartFile);
        fileName = imagePath + "/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .contentDisposition("inline")
                .key(fileName)
                .build();
        RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
        s3Client.putObject(putObjectRequest, requestBody);

        return fileName;
    }

    @Override
    public String getImageUrl(String fileName) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest).toString();
    }

    @Override
    public void deleteImageByFileName(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public String getFileName(MultipartFile multipartFile) {
        return GenerateFileName.buildFileName(multipartFile.getOriginalFilename());
    }

}
