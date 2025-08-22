package com.gig.collide.util.s3;

import com.gig.collide.util.s3.dto.BatchDeleteResult;
import com.gig.collide.util.s3.dto.FileInfo;
import com.gig.collide.util.s3.dto.UploadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AWS S3 文件管理控制器
 * 提供文件上传、下载、删除等REST API接口
 * 集成防盗链功能
 */
@Slf4j
@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    
    private final S3Service s3Service;
    private final AntiHotlinkUtil antiHotlinkUtil;
    
    /**
     * 文件上传接口
     *
     * @param file 要上传的文件
     * @param path 存储路径（可选）
     * @return 上传结果
     */
    @PostMapping("/upload")
    public ResponseEntity<UploadResult> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "path", required = false) String path) {
        
        try {
            // 生成对象键
            String objectKey = generateObjectKey(file.getOriginalFilename(), path);
            
            // 上传文件
            UploadResult result = s3Service.uploadFile(file, objectKey);
            
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            UploadResult errorResult = UploadResult.builder()
                    .success(false)
                    .errorMessage("文件上传失败: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
    
    /**
     * 文件下载接口（带防盗链检测）
     *
     * @param objectKey 对象键
     * @param request HTTP请求
     * @return 文件流
     */
    @GetMapping("/download/{objectKey:.+}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @PathVariable String objectKey,
            HttpServletRequest request) {
        
        try {
            // 防盗链检测
            if (!antiHotlinkUtil.isAllowedDomain(request)) {
                log.warn("防盗链检测失败，请求来源: {}", request.getHeader("Referer"));
                
                // 如果有配置重定向URL，则重定向
                String redirectUrl = antiHotlinkUtil.getRedirectUrl();
                if (redirectUrl != null && !redirectUrl.trim().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header(HttpHeaders.LOCATION, redirectUrl)
                            .build();
                }
                
                // 否则返回403禁止访问
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            // 检查文件是否存在
            if (!s3Service.fileExists(objectKey)) {
                return ResponseEntity.notFound().build();
            }
            
            // 获取文件信息
            FileInfo fileInfo = s3Service.getFileInfo(objectKey);
            
            // 下载文件
            var response = s3Service.downloadFile(objectKey);
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileInfo.getContentType()));
            headers.setContentLength(fileInfo.getSize());
            headers.setContentDispositionFormData("attachment", 
                    URLEncoder.encode(fileInfo.getObjectKey(), StandardCharsets.UTF_8));
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(response));
                    
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 生成预签名下载URL接口
     *
     * @param objectKey 对象键
     * @param expiration 过期时间（秒，默认1小时）
     * @return 预签名URL
     */
    @GetMapping("/presigned-url/{objectKey:.+}")
    public ResponseEntity<String> generatePresignedUrl(
            @PathVariable String objectKey,
            @RequestParam(value = "expiration", defaultValue = "3600") long expiration) {
        
        try {
            // 检查文件是否存在
            if (!s3Service.fileExists(objectKey)) {
                return ResponseEntity.notFound().build();
            }
            
            // 生成预签名URL
            String presignedUrl = s3Service.generatePresignedDownloadUrl(objectKey, expiration);
            
            return ResponseEntity.ok(presignedUrl);
            
        } catch (Exception e) {
            log.error("生成预签名URL失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 删除文件接口
     *
     * @param objectKey 对象键
     * @return 删除结果
     */
    @DeleteMapping("/{objectKey:.+}")
    public ResponseEntity<Boolean> deleteFile(@PathVariable String objectKey) {
        try {
            boolean success = s3Service.deleteFile(objectKey);
            
            if (success) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
            }
            
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
    
    /**
     * 批量删除文件接口
     *
     * @param objectKeys 对象键列表
     * @return 批量删除结果
     */
    @DeleteMapping("/batch")
    public ResponseEntity<BatchDeleteResult> batchDeleteFiles(@RequestBody List<String> objectKeys) {
        try {
            BatchDeleteResult result = s3Service.batchDeleteFiles(objectKeys);
            
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            
        } catch (Exception e) {
            log.error("批量删除文件失败: {}", e.getMessage(), e);
            BatchDeleteResult errorResult = BatchDeleteResult.builder()
                    .success(false)
                    .errorMessage("批量删除文件失败: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
    
    /**
     * 获取文件信息接口
     *
     * @param objectKey 对象键
     * @return 文件信息
     */
    @GetMapping("/info/{objectKey:.+}")
    public ResponseEntity<FileInfo> getFileInfo(@PathVariable String objectKey) {
        try {
            FileInfo fileInfo = s3Service.getFileInfo(objectKey);
            return ResponseEntity.ok(fileInfo);
            
        } catch (Exception e) {
            log.error("获取文件信息失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 检查文件是否存在接口
     *
     * @param objectKey 对象键
     * @return 是否存在
     */
    @GetMapping("/exists/{objectKey:.+}")
    public ResponseEntity<Boolean> fileExists(@PathVariable String objectKey) {
        try {
            boolean exists = s3Service.fileExists(objectKey);
            return ResponseEntity.ok(exists);
            
        } catch (Exception e) {
            log.error("检查文件是否存在失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 生成对象键
     *
     * @param originalFilename 原始文件名
     * @param path 存储路径
     * @return 对象键
     */
    private String generateObjectKey(String originalFilename, String path) {
        if (path != null && !path.trim().isEmpty()) {
            // 确保路径以/结尾
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            return path + originalFilename;
        } else {
            // 使用时间戳生成唯一文件名
            long timestamp = System.currentTimeMillis();
            String extension = "";
            if (originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            return "uploads/" + timestamp + extension;
        }
    }
}
