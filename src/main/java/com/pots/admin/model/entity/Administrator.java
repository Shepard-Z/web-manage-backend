package com.pots.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admin")
public class Administrator {
    
    private Long          id;
    private String        name;
    private String        gender;
    private String        introduction;
    private String        img_url;
    private Boolean       is_active;
    private String        created_by;
    private LocalDateTime create_time;
    private String        updated_by;
    private LocalDateTime update_time;
    
}
