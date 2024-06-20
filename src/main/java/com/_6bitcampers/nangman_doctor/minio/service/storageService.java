package com._6bitcampers.nangman_doctor.minio.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class storageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketname;

    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
    public void moveFilesToFinalBucket(List <String> imageUrls,List <String> uploadedUUIDs) {

        for (String key : imageUrls) {
            try {
                // Copy object to final bucket
                CopyObjectArgs copyObjectArgs = CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketname).object("temp/"+key).build())
                        .bucket(bucketname)
                        .object("/reviewBoard/"+key)
                        .build();
                minioClient.copyObject(copyObjectArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String tempObject : uploadedUUIDs) {
            // 큰따옴표와 대괄호 제거
            String cleanedObject = tempObject.replace("\"", "").replace("[", "").replace("]", "");

            deleteFile(bucketname, "/temp/" + cleanedObject);
        }
    }

    public InputStream getFile(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String bucketName, String objectName){
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();

        try {
            minioClient.removeObject(args);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}
