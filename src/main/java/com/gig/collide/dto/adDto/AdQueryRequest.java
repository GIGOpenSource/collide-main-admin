package com.gig.collide.dto.adDto;

import lombok.Data;

@Data
public class AdQueryRequest {
    private Integer page;
    private Integer size;
    private String adName;
    private String adTitle;
    private String adType;
    private Boolean isActive;
    private Long minSortOrder;
    private Long maxSortOrder;
}
