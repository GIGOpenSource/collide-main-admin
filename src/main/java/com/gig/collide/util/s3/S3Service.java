package com.gig.collide.util.s3;

import com.gig.collide.util.s3.dto.UploadResult;
import com.gig.collide.util.s3.dto.BatchDeleteResult;
import com.gig.collide.util.s3.dto.FileInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AWS S3 服务类
 * 提供文件上传、下载、删除等核心功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    
    private final S3Client s3Client;
    private final S3Config s3Config;
    
    /**
     * 上传文件到S3
     *
     * @param file 要上传的文件
     * @param objectKey 对象键（文件路径）
     * @return 上传结果
     */
    public UploadResult uploadFile(MultipartFile file, String objectKey) {
        try {
            // 检查文件大小
            if (file.isEmpty()) {
                throw new IllegalArgumentException("文件不能为空");
            }
            
            // 设置文件元数据
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();
            
            // 上传文件
            PutObjectResponse response = s3Client.putObject(putObjectRequest, 
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            
            log.info("文件上传成功: {}, ETag: {}", objectKey, response.eTag());
            
            return UploadResult.builder()
                    .success(true)
                    .objectKey(objectKey)
                    .etag(response.eTag())
                    .size(file.getSize())
                    .url(generatePublicUrl(objectKey))
                    .build();
                    
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            return UploadResult.builder()
                    .success(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }
    
    /**
     * 上传字节数组到S3
     *
     * @param data 字节数组
     * @param objectKey 对象键
     * @param contentType 内容类型
     * @return 上传结果
     */
    public UploadResult uploadBytes(byte[] data, String objectKey, String contentType) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .contentType(contentType)
                    .contentLength((long) data.length)
                    .build();
            
            PutObjectResponse response = s3Client.putObject(putObjectRequest, 
                    RequestBody.fromBytes(data));
            
            log.info("字节数组上传成功: {}, ETag: {}", objectKey, response.eTag());
            
            return UploadResult.builder()
                    .success(true)
                    .objectKey(objectKey)
                    .etag(response.eTag())
                    .size((long) data.length)
                    .url(generatePublicUrl(objectKey))
                    .build();
                    
        } catch (Exception e) {
            log.error("字节数组上传失败: {}", e.getMessage(), e);
            return UploadResult.builder()
                    .success(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }
    
    /**
     * 从S3下载文件
     *
     * @param objectKey 对象键
     * @return 文件输入流
     */
    public ResponseInputStream<GetObjectResponse> downloadFile(String objectKey) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .build();
            
            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
            log.info("文件下载成功: {}", objectKey);
            return response;
            
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 生成预签名下载URL（带过期时间）
     *
     * @param objectKey 对象键
     * @param expirationSeconds 过期时间（秒）
     * @return 预签名URL
     */
    public String generatePresignedDownloadUrl(String objectKey, long expirationSeconds) {
        try {
            // 创建 S3Presigner
            S3Presigner presigner = S3Presigner.builder()
                    .region(software.amazon.awssdk.regions.Region.of(s3Config.getRegion()))
                    .credentialsProvider(software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(
                            software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(
                                    s3Config.getAccessKeyId(),
                                    s3Config.getSecretAccessKey()
                            )
                    ))
                    .build();
            
            // 构建预签名请求
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(expirationSeconds))
                    .getObjectRequest(GetObjectRequest.builder()
                            .bucket(s3Config.getBucketName())
                            .key(objectKey)
                            .build())
                    .build();
            
            // 生成预签名URL
            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            String presignedUrl = presignedRequest.url().toString();
            
            // 关闭 presigner
            presigner.close();
            
            log.info("生成预签名下载URL成功: {}, 过期时间: {}秒", objectKey, expirationSeconds);
            return presignedUrl;
            
        } catch (Exception e) {
            log.error("生成预签名下载URL失败: {}", e.getMessage(), e);
            throw new RuntimeException("生成预签名下载URL失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 删除S3中的文件
     *
     * @param objectKey 对象键
     * @return 是否删除成功
     */
    public boolean deleteFile(String objectKey) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .build();
            
            DeleteObjectResponse response = s3Client.deleteObject(deleteObjectRequest);
            log.info("文件删除成功: {}", objectKey);
            return true;
            
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 批量删除文件
     *
     * @param objectKeys 对象键列表
     * @return 删除结果
     */
    public BatchDeleteResult batchDeleteFiles(List<String> objectKeys) {
        try {
            if (objectKeys.isEmpty()) {
                return BatchDeleteResult.builder()
                        .success(true)
                        .deletedCount(0)
                        .build();
            }
            
            List<ObjectIdentifier> objects = objectKeys.stream()
                    .map(key -> ObjectIdentifier.builder().key(key).build())
                    .collect(Collectors.toList());
            
            DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .delete(Delete.builder().objects(objects).build())
                    .build();
            
            DeleteObjectsResponse response = s3Client.deleteObjects(deleteObjectsRequest);
            
            log.info("批量删除文件成功，删除数量: {}", response.deleted().size());
            
            return BatchDeleteResult.builder()
                    .success(true)
                    .deletedCount(response.deleted().size())
                    .deletedKeys(objectKeys)
                    .build();
                    
        } catch (Exception e) {
            log.error("批量删除文件失败: {}", e.getMessage(), e);
            return BatchDeleteResult.builder()
                    .success(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }
    
    /**
     * 检查文件是否存在
     *
     * @param objectKey 对象键
     * @return 是否存在
     */
    public boolean fileExists(String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .build();
            
            s3Client.headObject(headObjectRequest);
            return true;
            
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 获取文件信息
     *
     * @param objectKey 对象键
     * @return 文件信息
     */
    public FileInfo getFileInfo(String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .build();
            
            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            
            return FileInfo.builder()
                    .objectKey(objectKey)
                    .size(response.contentLength())
                    .contentType(response.contentType())
                    .lastModified(response.lastModified())
                    .etag(response.eTag())
                    .build();
                    
        } catch (Exception e) {
            log.error("获取文件信息失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取文件信息失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 生成公共访问URL
     *
     * @param objectKey 对象键
     * @return 公共访问URL
     */
    private String generatePublicUrl(String objectKey) {
        if (s3Config.getEndpointUrl() != null && !s3Config.getEndpointUrl().trim().isEmpty()) {
            return s3Config.getEndpointUrl() + "/" + s3Config.getBucketName() + "/" + objectKey;
        } else {
            return String.format("https://%s.s3.%s.amazonaws.com/%s", 
                    s3Config.getBucketName(), s3Config.getRegion(), objectKey);
        }
    }
}
