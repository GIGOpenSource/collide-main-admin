# ContentChapterController 接口文档

## 概述

ContentChapterController 提供内容章节管理的完整CRUD操作，包括内容管理、章节管理、分类管理等功能。

## 基础信息

- **基础路径**: `/api/admin/content-chapter`
- **功能描述**: 内容章节管理Controller，提供内容管理、章节管理、分类管理等功能
- **作者**: why
- **版本**: 1.0
- **创建时间**: 2025-08-06
- **文档标准**: 以实际代码实现为准，确保100%一致性

## 接口列表

### 1. 内容列表查询

**接口地址**: `POST /api/admin/content-chapter/content/list`

**功能描述**: 查询内容列表，支持分页和多条件筛选

**方法签名**: `public Map<String, Object> getContentList(@RequestBody ContentQueryRequest request)`

**请求参数**:
```json
{
  "title": "小说标题",
  "contentType": "NOVEL",
  "authorId": 1001,
  "authorNickname": "作者昵称",
  "categoryId": 1,
  "categoryName": "分类名称",
  "status": "PUBLISHED",
  "reviewStatus": "APPROVED",
  "startTime": "2025-01-01 00:00:00",
  "endTime": "2025-01-31 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `title` (可选): 内容标题，支持模糊查询
- `contentType` (可选): 内容类型
- `authorId` (可选): 作者ID
- `authorNickname` (可选): 作者昵称，支持模糊查询
- `categoryId` (可选): 分类ID
- `categoryName` (可选): 分类名称，支持模糊查询
- `status` (可选): 状态
- `reviewStatus` (可选): 审核状态
- `startTime` (可选): 开始时间，格式：yyyy-MM-dd HH:mm:ss
- `endTime` (可选): 结束时间，格式：yyyy-MM-dd HH:mm:ss
- `page` (可选): 页码，从1开始
- `size` (可选): 每页大小

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 33,
        "title": "111",
        "description": "1111",
        "contentType": "NOVEL",
        "status": "DRAFT",
        "reviewStatus": "PENDING",
        "authorId": 1000000,
        "authorNickname": null,
        "authorAvatar": null,
        "coverUrl": "11",
        "categoryId": null,
        "categoryName": null,
        "tags": ["111"],
        "contentData": [
          {
            "contentId": 33,
            "chapterNum": 1,
            "title": "第一章",
            "fileUrl": "chapter1.txt",
            "status": "DRAFT"
          }
        ],
        "viewCount": 0,
        "likeCount": 0,
        "commentCount": 0,
        "favoriteCount": 0,
        "scoreCount": 0,
        "scoreTotal": 0,
        "publishTime": null,
        "totalWordCount": 0,
        "chapterCount": 1,
        "createTime": "2025-08-14T10:19:30",
        "updateTime": "2025-08-14T10:19:30"
      }
    ],
      "total": 3,
      "size": 10,
      "current": 1,
      "pages": 1
    }
  },
  "message": "查询成功"
}
```

**失败响应**:
```json
{
  "code": 3,
  "data": {
    "data": null
  },
  "message": "查询失败：具体错误信息"
}
```

### 2. 章节列表查询

**接口地址**: `POST /api/admin/content-chapter/chapters`

**功能描述**: 根据内容ID查询章节列表，支持按章节号、标题、状态等条件筛选

**方法签名**: `public Map<String, Object> getChaptersByContentId(@RequestBody ChapterQueryRequest request)`

**请求参数**:
```json
{
  "contentId": 1,
  "chapterNum": 1,
  "title": "第一章",
  "status": "PUBLISHED",
  "startTime": "2025-08-01 00:00:00",
  "endTime": "2025-08-06 23:59:59",
  "page": 1,
  "size": 10
}
```

**参数说明**:
- `contentId` (必填): 内容ID
- `chapterNum` (可选): 章节号
- `title` (可选): 章节标题，支持模糊查询
- `status` (可选): 状态
- `startTime` (可选): 开始时间，格式：yyyy-MM-dd HH:mm:ss
- `endTime` (可选): 结束时间，格式：yyyy-MM-dd HH:mm:ss
- `page` (可选): 页码，默认1
- `size` (可选): 每页大小，默认10

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "contentId": 1,
        "chapterNum": 1,
        "title": "第一章 开始",
        "content": "这是第一章的内容...",
        "wordCount": 2000,
        "status": "PUBLISHED",
        "contentType": "NOVEL",
        "authorId": 1001,
        "authorNickname": "作者昵称",
        "createTime": "2025-08-06T10:00:00",
        "updateTime": "2025-08-06T10:00:00"
      }
    ],
    "total": 20,
    "current": 1,
    "pages": 2
  },
  "message": "查询成功"
}
```

### 3. 章节详情查询

**接口地址**: `GET /api/admin/content-chapter/detail/{id}`

**功能描述**: 根据ID获取章节详情

**方法签名**: `public Map<String, Object> getChapterDetail(@PathVariable Long id)`

**路径参数**:
- `id`: 章节ID，Long类型

**请求示例**: `GET /api/admin/content-chapter/detail/1`

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "contentId": 1,
    "chapterNum": 1,
    "title": "第一章 开始",
    "content": "这是第一章的完整内容...",
    "wordCount": 2000,
    "status": "PUBLISHED",
    "contentType": "NOVEL",
    "authorId": 1001,
    "authorNickname": "作者昵称",
    "createTime": "2025-08-06T10:00:00",
    "updateTime": "2025-08-06T10:00:00"
  },
  "message": "查询成功"
}
```

### 4. 章节列表查询（通用）

**接口地址**: `POST /api/admin/content-chapter/list`

**功能描述**: 查询章节列表，支持多条件筛选

**方法签名**: `public Map<String, Object> getChapterList(@RequestBody ContentChapterQueryRequest request)`

**请求参数**:
```json
{
  "contentId": 1,
  "chapterNum": 1,
  "title": "章节标题",
  "status": "PUBLISHED",
  "startTime": "2025-08-01 00:00:00",
  "endTime": "2025-08-06 23:59:59",
  "page": 1,
  "size": 10
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "contentId": 1,
        "chapterNum": 1,
        "title": "第一章 开始",
        "content": "章节内容...",
        "status": "PUBLISHED",
        "createTime": "2025-08-06T10:00:00",
        "updateTime": "2025-08-06T10:00:00"
      }
    ],
    "total": 20,
    "current": 1,
    "pages": 2
  },
  "message": "查询成功"
}
```

### 5. 创建内容和章节

**接口地址**: `POST /api/admin/content-chapter/create`

**功能描述**: 创建内容和章节，支持同时创建内容和多个章节

**方法签名**: `public Map<String, Object> createContentWithChapters(@Valid @RequestBody ContentCreateRequest request)`

**请求参数**:
```json
{
  "title": "我的小说",
  "description": "这是一部精彩的小说...",
  "contentType": "NOVEL",
  "coverUrl": "https://example.com/cover.jpg",
  "tags": ["玄幻", "修仙", "热血"],
  "authorId": 1001,
  "authorNickname": "作者昵称",
  "authorAvatar": "https://example.com/avatar.jpg",
  "categoryId": 1,
  "categoryName": "玄幻小说",
  "status": "DRAFT",
  "chapters": [
    {
      "chapterNum": 1,
      "title": "第一章 开始",
      "content": "这是第一章的内容...",
      "status": "DRAFT"
    },
    {
      "chapterNum": 2,
      "title": "第二章 发展",
      "content": "这是第二章的内容...",
      "status": "DRAFT"
    }
  ]
}
```

**参数说明**:
- `title` (必填): 内容标题，不能为空
- `description` (可选): 内容描述
- `contentType` (必填): 内容类型，不能为空
- `coverUrl` (可选): 封面图片URL
- `tags` (可选): 标签列表
- `authorId` (可选): 作者用户ID
- `authorNickname` (可选): 作者昵称
- `authorAvatar` (可选): 作者头像URL
- `categoryId` (可选): 分类ID
- `categoryName` (可选): 分类名称
- `status` (可选): 状态，默认DRAFT
- `chapters` (可选): 章节列表

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "operationType": "CREATE_STORY",
    "contentData": {
      "id": 1,
      "title": "我的小说",
      "description": "这是一部精彩的小说...",
      "contentType": "NOVEL",
      "coverUrl": "https://example.com/cover.jpg",
      "authorNickname": "作者昵称",
      "tags": ["玄幻", "修仙", "热血"],
      "status": "DRAFT",
      "createTime": "2025-08-06T10:00:00",
      "updateTime": "2025-08-06T10:00:00"
    },
    "chapterDataList": [
      {
        "id": 1,
        "contentId": 1,
        "chapterNum": 1,
        "title": "第一章 开始",
        "content": "这是第一章的内容...",
        "fileUrl": null,
        "status": "DRAFT",
        "wordCount": 2500,
        "createTime": "2025-08-06T10:00:00",
        "updateTime": "2025-08-06T10:00:00"
      }
    ],
    "contentId": 1,
    "chapterIds": [1, 2],
    "status": "DRAFT",
    "viewCount": 0,
    "likeCount": 0,
    "commentCount": 0,
    "favoriteCount": 0,
    "totalWordCount": 5000,
    "chapterCount": 2,
    "createTime": "2025-08-06T10:00:00",
    "updateTime": "2025-08-06T10:00:00"
  },
  "message": "创建成功"
}
```

### 6. 修改内容审核状态

**接口地址**: `PUT /api/admin/content-chapter/review/{contentId}`

**功能描述**: 修改指定内容的审核状态

**方法签名**: `public Map<String, Object> updateReviewStatus(@PathVariable Long contentId, @RequestBody Map<String, String> request)`

**路径参数**:
- `contentId`: 内容ID，Long类型

**请求参数**:
```json
{
  "reviewStatus": "REJECTED"
}
```

**参数说明**:
- `reviewStatus` (必填): 审核状态，可选值：PENDING-待审核、APPROVED-审核通过、REJECTED-审核拒绝

**请求示例**: `PUT /api/admin/content-chapter/review/1`

**成功响应**:
```json
{
  "code": 0,
  "data": "审核状态修改成功",
  "message": "操作成功"
}
```

### 7. 编辑内容信息

**接口地址**: `PUT /api/admin/content-chapter/content/update`

**功能描述**: 编辑指定内容的基本信息

**方法签名**: `public Map<String, Object> updateContent(@Valid @RequestBody ContentUpdateRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "title": "修改后的小说标题",
  "description": "修改后的内容描述...",
  "contentType": "NOVEL",
  "contentData": "{\"key\": \"value\"}",
  "coverUrl": "https://example.com/new-cover.jpg",
  "tags": ["玄幻", "修仙", "热血"],
  "authorId": 1001,
  "authorNickname": "作者昵称",
  "authorAvatar": "https://example.com/avatar.jpg",
  "categoryId": 1,
  "categoryName": "玄幻小说",
  "status": "DRAFT"
}
```

**参数说明**:
- `id` (必填): 内容ID，不能为空
- `title` (必填): 内容标题，不能为空，长度不超过200个字符
- `description` (可选): 内容描述，长度不超过1000个字符
- `contentType` (必填): 内容类型，不能为空，格式：NOVEL|COMIC|VIDEO|ARTICLE|AUDIO
- `contentData` (可选): 内容数据，JSON格式字符串，长度不超过10000个字符
- `coverUrl` (可选): 封面图片URL，长度不超过500个字符，格式：https?://.*
- `tags` (可选): 标签列表，单个标签长度不超过50个字符
- `authorId` (必填): 作者用户ID，不能为空
- `authorNickname` (可选): 作者昵称，长度不超过100个字符
- `authorAvatar` (可选): 作者头像URL，长度不超过500个字符，格式：https?://.*
- `categoryId` (可选): 分类ID
- `categoryName` (可选): 分类名称，长度不超过100个字符
- `status` (可选): 状态，格式：DRAFT|PUBLISHED|OFFLINE

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "title": "修改后的小说标题",
    "description": "修改后的内容描述...",
    "contentType": "NOVEL",
    "contentData": "{\"key\": \"value\"}",
    "coverUrl": "https://example.com/new-cover.jpg",
    "tags": ["玄幻", "修仙", "热血"],
    "authorId": 1001,
    "authorNickname": "作者昵称",
    "authorAvatar": "https://example.com/avatar.jpg",
    "categoryId": 1,
    "categoryName": "玄幻小说",
    "status": "DRAFT",
    "reviewStatus": "PENDING",
    "viewCount": 0,
    "likeCount": 0,
    "commentCount": 0,
    "favoriteCount": 0,
    "scoreCount": 0,
    "scoreTotal": 0,
    "publishTime": null,
    "createTime": "2025-08-06T10:00:00",
    "updateTime": "2025-08-06T11:30:00"
  },
  "message": "编辑成功"
}
```

### 8. 删除内容

**接口地址**: `DELETE /api/admin/content-chapter/content/delete`

**功能描述**: 删除指定内容及其所有章节

**方法签名**: `public Map<String, Object> deleteContent(@Valid @RequestBody ContentDeleteRequest request)`

**请求参数**:
```json
{
  "id": 1,
  "deleteReason": "内容违规，包含不当信息",
  "operatorId": 1001,
  "operatorNickname": "管理员"
}
```

**参数说明**:
- `id` (必填): 内容ID
- `deleteReason` (可选): 删除原因
- `operatorId` (可选): 操作人ID
- `operatorNickname` (可选): 操作人昵称

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "title": "被删除的小说标题",
    "contentType": "NOVEL",
    "authorId": 1001,
    "authorNickname": "作者昵称",
    "deleteReason": "内容违规，包含不当信息",
    "operatorId": 1001,
    "operatorNickname": "管理员",
    "deleteTime": "2025-08-06T12:00:00",
    "deletedChapterCount": 20
  },
  "message": "删除成功"
}
```

### 9. 获取内容统计信息

**接口地址**: `GET /api/admin/content-chapter/statistics/{contentId}`

**功能描述**: 根据内容ID查询该内容的统计信息

**方法签名**: `public Map<String, Object> getContentStatistics(@PathVariable Long contentId)`

**路径参数**:
- `contentId`: 内容ID，Long类型

**请求示例**: `GET /api/admin/content-chapter/statistics/1`

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "contentId": 1,
    "contentTitle": "斗破苍穹",
    "contentType": "NOVEL",
    "authorNickname": "天蚕土豆",
    "totalLikeCount": 150,
    "totalCommentCount": 6,
    "comments": [
      {
        "commentId": 1,
        "userId": 10,
        "userNickname": "书迷小王",
        "userAvatar": "https://example.com/avatar1.jpg",
        "commentTime": "2025-08-06T10:30:00",
        "commentContent": "这部小说真的很精彩，萧炎的成长历程让人热血沸腾！",
        "commentLikeCount": 15,
        "status": "NORMAL",
        "parentCommentId": 0,
        "replyToUserId": null,
        "replyToUserNickname": null
      }
    ]
  },
  "message": "查询成功"
}
```

### 10. 删除评论

**接口地址**: `DELETE /api/admin/content-chapter/comment/delete`

**功能描述**: 删除指定评论

**方法签名**: `public Map<String, Object> deleteComment(@Valid @RequestBody CommentDeleteRequest request)`

**请求参数**:
```json
{
  "commentId": 1,
  "deleteReason": "评论违规，包含不当信息",
  "operatorId": 1001,
  "operatorNickname": "管理员"
}
```

**参数说明**:
- `commentId` (必填): 评论ID
- `deleteReason` (可选): 删除原因
- `operatorId` (可选): 操作人ID
- `operatorNickname` (可选): 操作人昵称

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "commentId": 1,
    "commentContent": "这部小说真的很精彩，萧炎的成长历程让人热血沸腾！",
    "userId": 10,
    "userNickname": "书迷小王",
    "targetId": 1,
    "deleteReason": "评论违规，包含不当信息",
    "operatorId": 1001,
    "operatorNickname": "管理员",
    "deleteTime": "2025-08-06T12:00:00"
  },
  "message": "删除成功"
}
```

### 11. 分类管理接口

#### 11.1 查询分类列表

**接口地址**: `POST /api/admin/content-chapter/category/list`

**功能描述**: 获取分类列表，支持分页和多条件筛选

**方法签名**: `public Map<String, Object> getCategoryList(@RequestBody CategoryQueryRequest request)`

**请求参数**:
```json
{
  "name": "小说",
  "parentId": 0,
  "parentName": "玄幻",
  "level": 1,
  "status": "active",
  "startTime": "2025-08-01 00:00:00",
  "endTime": "2025-08-06 23:59:59",
  "page": 1,
  "size": 10
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "data": [
      {
        "id": 1,
        "name": "玄幻小说",
        "description": "玄幻类型的小说分类",
        "parentId": 0,
        "parentName": null,
        "iconUrl": "https://example.com/icon1.png",
        "sort": 1,
        "level": 1,
        "contentCount": 150,
        "status": "active",
        "createTime": "2025-08-06T10:00:00",
        "updateTime": "2025-08-06T10:00:00"
      }
    ],
    "total": 20,
    "current": 1,
    "pages": 2
  },
  "message": "查询成功"
}
```

#### 11.2 获取分类详情

**接口地址**: `GET /api/admin/content-chapter/category/detail/{id}`

**功能描述**: 获取单个分类的完整详情信息

**方法签名**: `public Map<String, Object> getCategoryDetail(@PathVariable Long id)`

**路径参数**:
- `id`: 分类ID，Long类型

**请求示例**: `GET /api/admin/content-chapter/category/detail/1`

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "name": "玄幻小说",
    "description": "玄幻类型的小说分类",
    "parentId": 0,
    "parentName": null,
    "iconUrl": "https://example.com/icon1.png",
    "sort": 1,
    "level": 1,
    "contentCount": 150,
    "status": "active",
    "createTime": "2025-08-06T10:00:00",
    "updateTime": "2025-08-06T10:00:00"
  },
  "message": "查询成功"
}
```

#### 11.3 创建分类

**接口地址**: `POST /api/admin/content-chapter/category/create`

**功能描述**: 创建新的分类，支持多级分类结构

**方法签名**: `public Map<String, Object> createCategory(@Valid @RequestBody CategoryCreateRequest request)`

**请求参数**:
```json
{
  "name": "都市小说",
  "description": "都市类型的小说分类",
  "parentId": 0,
  "iconUrl": "https://example.com/icon2.png",
  "sort": 2
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 2,
    "name": "都市小说",
    "description": "都市类型的小说分类",
    "parentId": 0,
    "parentName": null,
    "iconUrl": "https://example.com/icon2.png",
    "sort": 2,
    "level": 1,
    "contentCount": 0,
    "status": "active",
    "createTime": "2025-08-06T11:00:00",
    "updateTime": "2025-08-06T11:00:00"
  },
  "message": "创建成功"
}
```

#### 11.4 编辑分类信息

**接口地址**: `PUT /api/admin/content-chapter/category/update`

**功能描述**: 编辑指定分类的信息

**方法签名**: `public Map<String, Object> updateCategory(@Valid @RequestBody CategoryUpdateRequest request)`

**请求参数**:
```json
{
  "id": 2,
  "name": "现代都市小说",
  "description": "现代都市背景的小说分类",
  "parentId": 0,
  "iconUrl": "https://example.com/new-icon2.png",
  "sort": 3
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 2,
    "name": "现代都市小说",
    "description": "现代都市背景的小说分类",
    "parentId": 0,
    "parentName": null,
    "iconUrl": "https://example.com/new-icon2.png",
    "sort": 3,
    "level": 1,
    "contentCount": 0,
    "status": "active",
    "createTime": "2025-08-06T11:00:00",
    "updateTime": "2025-08-06T12:00:00"
  },
  "message": "编辑成功"
}
```

#### 11.5 删除分类

**接口地址**: `DELETE /api/admin/content-chapter/category/delete`

**功能描述**: 删除指定分类，逻辑删除

**方法签名**: `public Map<String, Object> deleteCategory(@Valid @RequestBody CategoryDeleteRequest request)`

**请求参数**:
```json
{
  "id": 2,
  "deleteReason": "分类不再使用，进行清理",
  "operatorId": 1001,
  "operatorNickname": "管理员"
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "id": 2,
    "name": "现代都市小说",
    "deleteReason": "分类不再使用，进行清理",
    "operatorId": 1001,
    "operatorNickname": "管理员",
    "deleteTime": "2025-08-06T13:00:00"
  },
  "message": "删除成功"
}
```

#### 11.6 获取分类树结构

**接口地址**: `GET /api/admin/content-chapter/category/tree`

**功能描述**: 获取完整的分类树结构

**方法签名**: `public Map<String, Object> getCategoryTree()`

**请求示例**: `GET /api/admin/content-chapter/category/tree`

**成功响应**:
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "name": "小说",
      "parentId": 0,
      "parentName": null,
      "level": 1,
      "sort": 1,
      "iconUrl": "https://example.com/icon1.png",
      "children": [
        {
          "id": 2,
          "name": "玄幻小说",
          "parentId": 1,
          "parentName": "小说",
          "level": 2,
          "sort": 1,
          "iconUrl": "https://example.com/icon2.png",
          "children": []
        }
      ]
    }
  ],
  "message": "查询成功"
}
```

### 12. 统一内容操作接口

**接口地址**: `POST /api/admin/content-chapter/unified`

**功能描述**: 统一内容操作接口，根据operationType字段执行不同操作

**方法签名**: `public Map<String, Object> unifiedContentOperation(@Valid @RequestBody UnifiedContentRequest request)`

**请求参数示例**:

1. 创建文章/帖子：
```json
{
  "operationType": "CREATE_ARTICLE",
  "contentData": {
    "title": "文章标题",
    "content": "文章正文内容...",
    "tags": ["技术", "编程", "Java"],
    "mediaFiles": ["https://example.com/image1.jpg", "https://example.com/video1.mp4"]
  }
}
```

2. 创建小说/动漫/漫画：
```json
{
  "operationType": "CREATE_STORY",
  "contentData": {
    "title": "小说名称",
    "contentType": "NOVEL",
    "description": "小说简介",
    "coverUrl": "https://example.com/cover.jpg",
    "authorNickname": "作者昵称",
    "tags": ["玄幻", "修仙", "热血"]
  },
  "chapterDataList": [
    {
      "chapterNum": 1,
      "title": "第一章 开始",
      "content": "章节内容..."
    }
  ]
}
```

**成功响应**:
```json
{
  "code": 0,
  "data": {
    "success": true,
    "operationType": "CREATE_STORY",
    "message": "小说/动漫/漫画创建成功",
    "contentId": 123,
    "chapterIds": [456, 457]
  },
  "message": "操作成功"
}
```

## 业务规则说明

1. **参数验证**: 使用@Valid注解进行参数验证，确保必填字段和格式正确
2. **事务管理**: 创建内容和章节使用事务确保数据一致性
3. **软删除**: 删除操作支持软删除，不会物理删除数据
4. **分页查询**: 支持基于IPage的分页查询，包含records、total、current、size、pages等字段
5. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
6. **异常处理**: 所有接口都有完整的异常处理机制
7. **日志记录**: 所有操作都有详细的日志记录

## 数据库字段说明

### Content实体字段映射
- `id` → 主键ID
- `title` → 内容标题
- `description` → 内容描述
- `content_type` → 内容类型
- `content_data` → 内容数据(JSON)
- `cover_url` → 封面图片URL
- `tags` → 标签列表
- `author_id` → 作者ID
- `author_nickname` → 作者昵称
- `author_avatar` → 作者头像URL
- `category_id` → 分类ID
- `category_name` → 分类名称
- `status` → 状态
- `review_status` → 审核状态
- `view_count` → 浏览量
- `like_count` → 点赞数
- `comment_count` → 评论数
- `favorite_count` → 收藏数
- `score_count` → 评分人数
- `score_total` → 评分总分
- `publish_time` → 发布时间
- `total_word_count` → 总字数
- `chapter_count` → 章节数
- `create_time` → 创建时间
- `update_time` → 更新时间

### ContentChapter实体字段映射
- `id` → 主键ID
- `content_id` → 内容ID
- `chapter_num` → 章节号
- `title` → 章节标题
- `content` → 章节内容
- `file_url` → 文件URL
- `status` → 状态
- `word_count` → 字数
- `create_time` → 创建时间
- `update_time` → 更新时间

## 错误码说明

- `0`: 成功
- `3`: 失败

## 注意事项

1. **权限要求**: 所有接口都需要管理员权限
2. **参数验证**: 创建和更新接口使用@Valid注解进行参数验证
3. **响应格式**: 统一使用ResponseUtil工具类，确保响应格式一致
4. **分页查询**: 查询接口支持分页，使用IPage包装返回结果
5. **异常处理**: 所有接口都有完整的异常处理机制
6. **日志记录**: 所有操作都有详细的日志记录
7. **事务管理**: 涉及数据修改的操作都有事务保护
8. **字段查询限制**: 当前实现中，查询条件基于实际SQL查询字段，确保查询条件与实际数据库字段一致
9. **跨域支持**: 使用@CrossOrigin注解支持跨域请求
10. **MyBatis-Plus**: 使用MyBatis-Plus进行数据库操作，支持BaseMapper和ServiceImpl
