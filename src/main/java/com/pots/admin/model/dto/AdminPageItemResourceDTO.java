package com.pots.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pots.common.model.EntityBase;
import lombok.*;

// フロントからの fileBody をスルー
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminPageItemResourceDTO extends EntityBase {
    // フロント用
    private Long id;
    private Long pageItemId;
    
    // 資源テーブル
    private String resourceType;
    private String resLangCode;
    private String resSortOrder;
    private String url;
    
    // フロント から データの操作（新規か更新か）マーク
    private String itemStatus;
}
