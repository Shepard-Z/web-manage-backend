package com.pots.admin.service;

import com.pots.admin.model.dto.AdminAllUpdatePageDataDTO;
import com.pots.admin.model.dto.AdminPageItemDTO;
import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.ro.PageRO;
import com.pots.admin.model.ro.RemoveDataRO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AdminPageUpdateService {
    
    // id の付与とデータの収集
    AdminAllUpdatePageDataDTO assignIdAndCollectData(PageRO pageRO,Map<Long,MultipartFile> filesMap);
    
    // ファイルの保存
    Map<Long,String> savePageFiles(Map<Long,MultipartFile> fileMap);
    
    // ファイルの削除(臨時ディレクトリに移動)
    boolean movePageFilesToTempOld(List<RemoveDataRO> targetResList);
    
    // 新規画面要素
    boolean addNewPageItemList(List<AdminPageItemDTO> pageItemList);
    
    // 新規多言語データ
    boolean addNewPageI18nList(List<AdminPageItemI18nDTO> pageItemI18nDTO);
    
    // 新規資源データ
    boolean addNewPageResList(List<AdminPageItemResourceDTO> pageItemResource);
    
    // 多言語データの更新
    boolean modifyPageI18nList(List<AdminPageItemI18nDTO> pageItemI18nList);
    
    // 資源データの更新
    boolean modifyPageResList(List<AdminPageItemResourceDTO> pageItemResList);
    
    // 画面要素と子データの削除
    boolean deletePageItemListAndChild(List<RemoveDataRO> pageItemList);
    
    // 多言語データの削除
    boolean deletePageI18nList(List<RemoveDataRO> pageItemI18nList);
    
    // 資源データの削除
    boolean deletePageResList(List<RemoveDataRO> pageItemResList);
    
}
