package com.gig.collide.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gig.collide.domain.tag.Tag;
import com.gig.collide.dto.tagDto.TagCreateRequest;
import com.gig.collide.dto.tagDto.TagDTO;
import com.gig.collide.dto.tagDto.TagQueryRequest;
import com.gig.collide.dto.tagDto.TagUpdateRequest;
import com.gig.collide.mapper.TagMapper;
import com.gig.collide.service.Impl.TagService;
import com.gig.collide.util.PageQueryUtil;
import com.gig.collide.util.ValidationUtil;
import com.gig.collide.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
     * 标签Service实现类
     */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public IPage<TagDTO> getTagList(TagQueryRequest request) {
        log.info("查询标签列表，参数：{}", request);

        // 参数验证和默认值设置
        if (request == null) {
            request = new TagQueryRequest();
        }
        
        // 使用通用分页查询工具类
        return PageQueryUtil.executePageQuery(
            request,
            tagMapper::selectTagCount,
            req -> {
                long offset = com.gig.collide.util.PageUtil.calculateOffset(req.getPage(), req.getSize());
                return tagMapper.selectTagListWithLimit(req, offset, req.getSize());
            }
        );
    }

    @Override
    public IPage<TagDTO> searchTags(TagQueryRequest request) {
        log.info("条件查询标签列表，参数：{}", request);

        // 使用与getTagList相同的分页查询逻辑
        return PageQueryUtil.executePageQuery(
            request,
            tagMapper::selectTagCount,
            req -> {
                long offset = com.gig.collide.util.PageUtil.calculateOffset(req.getPage(), req.getSize());
                return tagMapper.selectTagListWithLimit(req, offset, req.getSize());
            }
        );
     }

     @Override
     public TagDTO addTag(TagCreateRequest request) {
         try {
             log.info("新增标签，参数：{}", request);

             // 使用工具类验证
             ValidationUtil.validateRequest(request, "标签创建请求");
             ValidationUtil.validateNotBlank(request.getName(), "标签名称");
             ValidationUtil.validateNotBlank(request.getTagType(), "标签类型");

             // 检查标签名称在同一类型下是否已存在
             TagQueryRequest checkRequest = new TagQueryRequest();
             checkRequest.setName(request.getName().trim());
             checkRequest.setTagType(request.getTagType().trim());
             
             Long count = tagMapper.selectTagCount(checkRequest);
             if (count > 0) {
                 throw new IllegalArgumentException("标签名称在同一类型下已存在");
             }

             // 创建标签实体
             Tag tag = new Tag();
             tag.setName(request.getName().trim());
             tag.setDescription(request.getDescription());
             tag.setTagType(request.getTagType().trim());
             tag.setCategoryId(request.getCategoryId());
             tag.setUsageCount(0L);
             tag.setStatus("active");

             // 保存到数据库
             int result = tagMapper.insert(tag);
             if (result <= 0) {
                 throw new RuntimeException("保存标签失败");
             }

             // 转换为DTO返回
             TagDTO tagDTO = new TagDTO();
             BeanUtils.copyProperties(tag, tagDTO);

             log.info("新增标签成功，ID：{}", tag.getId());
             return tagDTO;
             
         } catch (Exception e) {
             throw ExceptionUtil.handleBusinessException(e, "创建标签");
         }
     }

     @Override
     public TagDTO updateTag(TagUpdateRequest request) {
         try {
             log.info("编辑标签，参数：{}", request);

             // 使用工具类验证
             ValidationUtil.validateRequest(request, "标签更新请求");
             ValidationUtil.validateIdPositive(request.getId(), "标签");
             ValidationUtil.validateNotBlank(request.getName(), "标签名称");
             ValidationUtil.validateNotBlank(request.getTagType(), "标签类型");

             // 检查标签是否存在
             Tag existingTag = tagMapper.selectById(request.getId());
             ValidationUtil.validateEntityExists(existingTag, "标签", request.getId());

             // 检查标签名称在同一类型下是否已存在（排除当前标签）
             TagQueryRequest checkRequest = new TagQueryRequest();
             checkRequest.setName(request.getName().trim());
             checkRequest.setTagType(request.getTagType().trim());
             
             List<TagDTO> existingTags = tagMapper.selectTagListWithLimit(checkRequest, 0L, 10L);
             for (TagDTO existingTagDTO : existingTags) {
                 if (!existingTagDTO.getId().equals(request.getId())) {
                     throw new IllegalArgumentException("标签名称在同一类型下已存在");
                 }
             }

             // 更新标签信息
             Tag tag = new Tag();
             tag.setId(request.getId());
             tag.setName(request.getName().trim());
             tag.setDescription(request.getDescription());
             tag.setTagType(request.getTagType().trim());
             tag.setCategoryId(request.getCategoryId());
             tag.setStatus(request.getStatus() != null ? request.getStatus() : existingTag.getStatus());

             // 更新到数据库
             int result = tagMapper.updateById(tag);
             if (result <= 0) {
                 throw new RuntimeException("更新标签失败");
             }

             // 转换为DTO返回
             TagDTO tagDTO = new TagDTO();
             BeanUtils.copyProperties(tag, tagDTO);

             log.info("编辑标签成功，ID：{}", tag.getId());
             return tagDTO;
             
         } catch (Exception e) {
             throw ExceptionUtil.handleBusinessException(e, "更新标签");
         }
     }

     @Override
     public TagDTO getTagById(Long id) {
         log.info("根据ID获取标签详情，ID：{}", id);

         if (id == null) {
             throw new IllegalArgumentException("标签ID不能为空");
         }

         // 查询标签
         Tag tag = tagMapper.selectById(id);
         if (tag == null) {
             throw new IllegalArgumentException("标签不存在");
         }

         // 转换为DTO返回
         TagDTO tagDTO = new TagDTO();
         BeanUtils.copyProperties(tag, tagDTO);

         log.info("获取标签详情成功，ID：{}", id);
         return tagDTO;
     }

     @Override
     public boolean deleteTag(Long id) {
         try {
             log.info("删除标签，ID：{}", id);

             ValidationUtil.validateIdPositive(id, "标签");

             // 检查标签是否存在
             Tag existingTag = tagMapper.selectById(id);
             ValidationUtil.validateEntityExists(existingTag, "标签", id);

             // 检查标签是否正在被使用
             if (existingTag.getUsageCount() > 0) {
                 throw new IllegalArgumentException("标签正在被使用，无法删除");
             }

             // 删除标签
             int result = tagMapper.deleteById(id);
             if (result <= 0) {
                 throw new RuntimeException("删除标签失败");
             }

             log.info("删除标签成功，ID：{}", id);
             return true;
             
         } catch (Exception e) {
             throw ExceptionUtil.handleBusinessException(e, "删除标签");
         }
     }
} 