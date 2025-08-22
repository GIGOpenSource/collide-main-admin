package com.gig.collide.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gig.collide.domain.blo.BloPython;
import com.gig.collide.dto.bloPythonDto.BloPythonDTO;
import com.gig.collide.dto.bloPythonDto.BloPythonQueryRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonCreateRequest;
import com.gig.collide.dto.bloPythonDto.BloPythonUpdateRequest;
import com.gig.collide.util.PageResult;

/**
 * Python爬虫博主信息服务接口
 * 
 * @author why
 * @since 2025-01-27
 * @version 1.0
 */
public interface BloPythonService extends IService<BloPython> {

    /**
     * 查询Python爬虫博主信息（支持条件查询和分页）
     * @param request 查询条件
     * @return 分页结果
     */
    PageResult<BloPythonDTO> queryBloPythons(BloPythonQueryRequest request);

    /**
     * 创建Python爬虫博主
     * @param request 创建请求
     * @return 是否成功
     */
    boolean createBloPython(BloPythonCreateRequest request);

    /**
     * 更新Python爬虫博主信息
     * @param request 更新请求
     * @return 是否成功
     */
    boolean updateBloPython(BloPythonUpdateRequest request);

    /**
     * 删除Python爬虫博主
     * @param id 博主ID
     * @return 是否成功
     */
    boolean deleteBloPython(Long id);
}
