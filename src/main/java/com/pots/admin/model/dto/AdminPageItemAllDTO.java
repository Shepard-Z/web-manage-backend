package com.pots.admin.model.dto;

import com.pots.common.model.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AdminPageItemAllDTO extends EntityBase {
    
    // 主テーブル
    private Long   id;              // not null
    private String pageName;        // not null
    private String itemCategory;    // not null
    // 多言語テーブル
    private Long   i18nId;
    private String langCode;
    private String title;
    private String subTitle;
    private String introduction;
    // 資源テーブル
    private Long   resId;
    private String resourceType;
    private String resLangCode;
    private String resSortOrder;
    private String url;
    
}
