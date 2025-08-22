package com.gig.collide.dto.adDto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdDTO {
    private Long id;
    private String adName;
    private String adTitle;
    private String adDescription;
    private String adType;
    private String imageUrl;
    private String clickUrl;
    private String altText;
    private String targetType;
    private Boolean isActive;
    private Long sortOrder;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
