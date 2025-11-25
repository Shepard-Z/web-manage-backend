package com.pots.admin.model.dto;

import com.pots.common.model.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AdminPageItemDTO extends EntityBase {
    // 主テーブル
    private Long   id;              // not null
    private String pageName;        // not null
    private String itemCategory;    // not null
    
    // フロント から データの操作（新規か更新か）マーク
    private String itemStatus;
}
