package com.pots.admin.coordinator.impl;

import com.pots.admin.coordinator.FullUpdateCoordinator;
import com.pots.admin.model.dto.AdminAllUpdatePageDataDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.ro.PageRO;
import com.pots.admin.model.ro.RemoveDataRO;
import com.pots.admin.service.AdminPageItemService;
import com.pots.admin.service.AdminPageUpdateService;
import com.pots.admin.service.AdminUserService;
import com.pots.common.constant.MessageConst;
import com.pots.common.model.ResultType;
import com.pots.common.utils.MyFileUtils;
import com.pots.common.utils.OriginException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FullUpdateCoordinatorImpl implements FullUpdateCoordinator{
    
    @Autowired
    AdminPageUpdateService adminPageUpdateService;
    @Autowired
    AdminPageItemService   adminPageItemService;
    @Autowired
    AdminUserService       adminUserService;
    
    /* 2025.09.24
     pageRO にある pageItemArr が必ず新規であり、pageItemArr が新規の場合、その内部にある i18nArr、resourceArrも必ず新規である
     pageI18nArr、pageItemResourceArr が必ず既存の page_item テーブルのデータ に属する
     removeDataArr に削除操作のデータのみ格納されている
     */
    @Transactional
    @Override
    public ResultType pageItem(PageRO pageRO,Map<Long,MultipartFile> filesMap) {
        
        // 新規データに id を付与、処理操作によるデータの分類
        AdminAllUpdatePageDataDTO allPageData = adminPageUpdateService.assignIdAndCollectData(pageRO,filesMap);
        
        // 新規 pageItem データの追加
        if (!adminPageUpdateService.addNewPageItemList(allPageData.getNewPageItem())) {
            log.warn(MessageConst.PAGE_ITEM_INSERT_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_INSERT_FAILED);
        }
        
        // 新規 pageItemI18n データの追加
        if (!adminPageUpdateService.addNewPageI18nList(allPageData.getNewPageItemI18n())) {
            log.warn(MessageConst.PAGE_ITEM_I18N_INSERT_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_I18N_INSERT_FAILED);
        }
        
        // 新規 pageItemRes データの追加
        if (!adminPageUpdateService.addNewPageResList(allPageData.getNewPageItemRes())) {
            log.warn(MessageConst.PAGE_ITEM_RES_INSERT_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_RES_INSERT_FAILED);
        }
        
        // pageItemI18n の更新
        if (!adminPageUpdateService.modifyPageI18nList(allPageData.getModifyPageItemI18n())) {
            log.warn(MessageConst.PAGE_ITEM_I18N_UPDATE_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_I18N_UPDATE_FAILED);
        }
        
        // pageItemRes の更新
        if (!adminPageUpdateService.modifyPageResList(allPageData.getModifyPageItemRes())) {
            log.warn(MessageConst.PAGE_ITEM_RES_UPDATE_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_RES_UPDATE_FAILED);
        }
        
        // 削除処理（子データから削除すべき）
        // pageItemI18n の削除
        if (!adminPageUpdateService.deletePageI18nList(allPageData.getDeletePageItemI18n())) {
            log.warn(MessageConst.PAGE_ITEM_I18N_DELETE_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_I18N_DELETE_FAILED);
        }
        // ファイルの削除(臨時ディレクトリに移動)
        if (!adminPageUpdateService.movePageFilesToTempOld(allPageData.getDeletePageItemRes())) {
            log.warn(MessageConst.FAILED_TO_MOVE_OLD_FILE,"UNKNOWN");
            throw new OriginException(ResultType.FAILED_TO_MOVE_OLD_FILE,"UNKNOWN");
        }
        // pageItemRes の削除
        if (!adminPageUpdateService.deletePageResList(allPageData.getDeletePageItemRes())) {
            log.warn(MessageConst.PAGE_ITEM_RES_DELETE_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_RES_DELETE_FAILED);
        }
        
        // pageItem に属する pageItemRes の取得（属する pageItemRes がなくても続行できる）
        List<Long> idList =
                allPageData.getDeletePageItem().stream().map(RemoveDataRO::getIdForRemove).toList();
        List<AdminPageItemResourceDTO> resList = adminPageItemService.getPageItemResByItemId(idList);
        
        List<RemoveDataRO> removeResList = resList.stream().map(resourceDTO -> {
            RemoveDataRO ro = new RemoveDataRO();
            ro.setIdForRemove(resourceDTO.getId());
            ro.setFileUrl(resourceDTO.getUrl());
            ro.setResourceType(resourceDTO.getResourceType());
            return ro;
        }).toList();
        // pageItem に属する pageItemRes のファイルの削除(臨時ディレクトリに移動)
        if (!adminPageUpdateService.movePageFilesToTempOld(removeResList)) {
            log.warn(MessageConst.FAILED_TO_DELETE_TARGET_FILE,"UNKNOWN");
            throw new OriginException(ResultType.FAILED_TO_DELETE_TARGET_FILE,"UNKNOWN");
        }
        
        // pageItem の削除
        if (!adminPageUpdateService.deletePageItemListAndChild(allPageData.getDeletePageItem())) {
            log.warn(MessageConst.PAGE_ITEM_DELETE_FAILED);
            throw new OriginException(ResultType.PAGE_ITEM_DELETE_FAILED);
        }
        
        // ファイルの最終処理(臨時ディレクトリから、実のディレクトリに反映する)
        if(!MyFileUtils.applyFiles()) throw new OriginException(ResultType.FAILED_TO_APPLY_FILES);
        return ResultType.SUCCESS;
    }
    
    @Override
    public ResultType game() {
        return null;
    }
    
    @Override
    public ResultType chara() {
        return null;
    }

    @Override
    public ResultType checkUser(HttpServletRequest request) {
        return adminUserService.checkUser(request);
    }
}
