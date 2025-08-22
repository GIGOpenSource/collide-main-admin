# AWS S3 对象存储功能使用说明

## 概述

本项目集成了AWS S3对象存储服务，提供完整的文件上传、下载、删除功能，并内置防盗链保护机制。

## 功能特性

- ✅ 文件上传（支持MultipartFile和字节数组）
- ✅ 文件下载（带防盗链检测）
- ✅ 预签名URL生成（带过期时间）
- ✅ 文件删除（单个和批量）
- ✅ 文件信息查询
- ✅ 防盗链保护（支持域名白名单和通配符）
- ✅ 支持自定义S3兼容端点（如MinIO）

## 配置说明

### 1. 环境变量配置

在系统环境变量中设置以下值：

```bash
# AWS 访问密钥
export AWS_ACCESS_KEY_ID=your-access-key-id
export AWS_SECRET_ACCESS_KEY=your-secret-access-key

# AWS 区域
export AWS_REGION=us-east-1

# S3 存储桶名称
export S3_BUCKET_NAME=your-bucket-name

# 可选：S3兼容端点（如MinIO）
export S3_ENDPOINT_URL=http://localhost:9000
```

### 2. 配置文件

在 `application.yml` 中添加以下配置：

```yaml
aws:
  s3:
    access-key-id: ${AWS_ACCESS_KEY_ID:your-access-key-id}
    secret-access-key: ${AWS_SECRET_ACCESS_KEY:your-secret-access-key}
    region: ${AWS_REGION:us-east-1}
    bucket-name: ${S3_BUCKET_NAME:your-bucket-name}
    
    anti-hotlink:
      enabled: true
      allowed-domains:
        - "localhost"
        - "*.yourdomain.com"
        - "yourdomain.com"
      signature-expiration: 3600
```

## API 接口

### 文件上传

```http
POST /api/s3/upload
Content-Type: multipart/form-data

file: [文件]
path: [可选] 存储路径
```

**响应示例：**
```json
{
  "success": true,
  "objectKey": "uploads/1234567890.jpg",
  "etag": "\"abc123\"",
  "size": 1024,
  "url": "https://bucket.s3.region.amazonaws.com/uploads/1234567890.jpg"
}
```

### 文件下载

```http
GET /api/s3/download/{objectKey}
Referer: [来源域名]
```

**防盗链说明：**
- 请求必须包含有效的Referer头
- 域名必须在白名单中
- 支持通配符匹配（如 `*.example.com`）

### 预签名URL

```http
GET /api/s3/presigned-url/{objectKey}?expiration=3600
```

**响应：** 预签名的下载URL（字符串）

### 文件删除

```http
DELETE /api/s3/{objectKey}
```

### 批量删除

```http
DELETE /api/s3/batch
Content-Type: application/json

["file1.jpg", "file2.jpg"]
```

### 文件信息

```http
GET /api/s3/info/{objectKey}
```

**响应示例：**
```json
{
  "objectKey": "uploads/1234567890.jpg",
  "size": 1024,
  "contentType": "image/jpeg",
  "lastModified": "2024-01-01T00:00:00Z",
  "etag": "\"abc123\""
}
```

### 文件存在性检查

```http
GET /api/s3/exists/{objectKey}
```

## 防盗链配置

### 域名白名单格式

- `*` - 允许所有域名
- `example.com` - 只允许example.com
- `*.example.com` - 允许所有example.com的子域名
- `example.*` - 允许以example开头的域名

### 防盗链重定向

当防盗链检测失败时，可以配置重定向URL：

```yaml
aws:
  s3:
    anti-hotlink:
      redirect-url: "https://yourdomain.com/access-denied"
```

## 使用示例

### Java代码示例

```java
@Service
public class FileService {
    
    @Autowired
    private S3Service s3Service;
    
    public void uploadFile(MultipartFile file) {
        // 上传文件
        UploadResult result = s3Service.uploadFile(file, "documents/" + file.getOriginalFilename());
        
        if (result.isSuccess()) {
            System.out.println("文件上传成功：" + result.getUrl());
        }
    }
    
    public void downloadFile(String objectKey) {
        // 下载文件
        var response = s3Service.downloadFile(objectKey);
        // 处理文件流...
    }
    
    public String getPresignedUrl(String objectKey) {
        // 生成1小时有效的预签名URL
        return s3Service.generatePresignedDownloadUrl(objectKey, 3600);
    }
}
```

### 前端JavaScript示例

```javascript
// 文件上传
async function uploadFile(file) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('path', 'images');
    
    const response = await fetch('/api/s3/upload', {
        method: 'POST',
        body: formData
    });
    
    const result = await response.json();
    if (result.success) {
        console.log('上传成功：', result.url);
    }
}

// 文件下载（需要Referer头）
async function downloadFile(objectKey) {
    const response = await fetch(`/api/s3/download/${objectKey}`, {
        headers: {
            'Referer': window.location.origin
        }
    });
    
    if (response.ok) {
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = objectKey.split('/').pop();
        a.click();
    }
}
```

## 注意事项

1. **安全性**：请妥善保管AWS访问密钥，不要提交到代码仓库
2. **防盗链**：Referer头可以被伪造，建议结合其他安全措施
3. **文件大小**：默认最大文件大小为100MB，可在配置中调整
4. **存储成本**：注意AWS S3的存储和传输成本
5. **权限配置**：确保S3存储桶的权限配置正确

## 故障排除

### 常见问题

1. **上传失败**：检查AWS凭证和存储桶权限
2. **下载403**：检查防盗链配置和Referer头
3. **预签名URL过期**：调整过期时间配置
4. **文件不存在**：检查对象键是否正确

### 日志查看

查看应用日志中的S3相关错误信息：

```bash
tail -f logs/application.log | grep S3
```

## 扩展功能

如需添加其他功能，可以：

1. 在 `S3Service` 中添加新的方法
2. 在 `S3Controller` 中添加新的API端点
3. 扩展防盗链规则（如IP白名单、时间限制等）
4. 添加文件压缩、格式转换等功能
