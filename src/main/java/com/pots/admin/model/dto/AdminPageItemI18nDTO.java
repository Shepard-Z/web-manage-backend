package com.pots.admin.model.dto;

import com.pots.common.model.EntityBase;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminPageItemI18nDTO extends EntityBase {
    // フロント用
    private Long id;
    private Long pageItemId;
    
    // 多言語テーブル
    private String langCode;
    private String title;
    private String subtitle;
    private String introduction;
    
    // フロント から データの操作（新規か更新か）マーク
    private String itemStatus;
}
