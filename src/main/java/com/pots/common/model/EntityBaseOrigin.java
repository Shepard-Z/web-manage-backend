package com.pots.common.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public abstract class EntityBaseOrigin {
    
    private Long          creatorId;    // not null
    private LocalDateTime createTime;   // not null default CURRENT_TIMESTAMP
    private Long          updaterId;    // default null
    private LocalDateTime updateTime;   // default null on update CURRENT_TIMESTAMP
    
}
