package com.pots.admin.model.ro;

import com.pots.common.constant.FrontConst;
import lombok.Data;

@Data
public class RemoveDataRO {
    
    // 削除するデータの分類(テーブルの区分)
    private String uploadCategory;
    // 削除する対象の資源分類(上記 uploadCategory == FrontConst.UploadCategory.PAGE_RESOURCE の場合のみ、このフィールに意味がある)
    private String resourceType;
    // 削除するデータのid
    private Long   idForRemove;
    // parentId == -777 の時、このデータが主テーブルのデータを意味する
    private Long   parentId;
    // ファイルのurl
    private String fileUrl;
    
}
