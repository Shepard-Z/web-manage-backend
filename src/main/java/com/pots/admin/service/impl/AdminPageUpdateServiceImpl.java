package com.pots.admin.service.impl;

import com.pots.admin.mapper.AdminPageItemMapper;
import com.pots.admin.model.dto.AdminAllUpdatePageDataDTO;
import com.pots.admin.model.dto.AdminPageItemDTO;
import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.ro.PageRO;
import com.pots.admin.model.ro.RemoveDataRO;
import com.pots.admin.service.AdminPageUpdateService;
import com.pots.common.constant.FrontConst;
import com.pots.common.constant.MessageConst;
import com.pots.common.constant.PropConst;
import com.pots.common.constant.UrlConst;
import com.pots.common.model.ResultType;
import com.pots.common.utils.MyFileUtils;
import com.pots.common.utils.OriginException;
import com.pots.common.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AdminPageUpdateServiceImpl implements AdminPageUpdateService {
    
    private final SnowflakeIdWorker   snowflakeIdWorker;
    private final AdminPageItemMapper adminPageItemMapper;
    
    @Autowired
    public AdminPageUpdateServiceImpl(SnowflakeIdWorker snowflakeIdWorker,AdminPageItemMapper adminPageItemMapper) {
        this.snowflakeIdWorker = snowflakeIdWorker;
        this.adminPageItemMapper = adminPageItemMapper;
    }
    
    // 一回のループで、全部の事前処理を行う
    @Override
    public AdminAllUpdatePageDataDTO assignIdAndCollectData(PageRO pageRO,Map<Long,MultipartFile> filesMap) {
        
        // ファイルの保存とパスの取得
        Map<Long,String> filePath = savePageFiles(filesMap);
        
        // ファイル（画像）の保存
        AdminAllUpdatePageDataDTO allPageData = new AdminAllUpdatePageDataDTO();
        
        // 新規 pageItem に id を付与
        pageRO.getPageItemArr().forEach(item -> {
            // データが新規ではない場合( 2025.09.24 の時点で、 pageRO にある pageItemArr は必ず新規である )
            if (!item.getItemStatus().equals(FrontConst.ItemStatus.NEW)) {
                log.warn(MessageConst.ILLEGAL_ITEM_STATUS + ": ",item);
                throw new OriginException(ResultType.ILLEGAL_ITEM_STATUS);
            }
            
            // 親データに新 id を付与
            item.setId(snowflakeIdWorker.nextId());
            
            // 子データ i18n の処理
            item.getI18nArr().forEach(i18n -> {
                // 新id を付与
                i18n.setId(snowflakeIdWorker.nextId());
                // リンクid を付与
                i18n.setPageItemId(item.getId());
                // 新規データの収集
                allPageData.getNewPageItemI18n().add(i18n);
                // クリエイターidの書き込み
                i18n.setCreatorId(PropConst.SYSTEM_ID);
            });
            
            // 子データ resource の処理
            item.getResourceArr().forEach(resource -> {
                // 関連ファイルがある場合、旧データの削除と関連ファイルのパスを取得
                String fileUrl = filePath.remove(resource.getId());
                // 新id を付与
                resource.setId(snowflakeIdWorker.nextId());
                // リンクid を付与
                resource.setPageItemId(item.getId());
                // 画像データがある場合
                if (fileUrl != null) {
                    // 関連するファイルのurlを書き込む
                    resource.setUrl(fileUrl);
                }
                // 新規データの収集
                allPageData.getNewPageItemRes().add(resource);
                // クリエイターidの書き込み
                resource.setCreatorId(PropConst.SYSTEM_ID);
            });
            
            // クリエイターidの書き込み
            item.setCreatorId(PropConst.SYSTEM_ID);
            // 新規データの収集
            allPageData.getNewPageItem().add(item);
            
        });
        
        // 既存の pageItemI18n の場合
        pageRO.getPageItemI18nArr().forEach(i18n -> {
            // 子データが新規の場合
            if (i18n.getItemStatus().equals(FrontConst.ItemStatus.NEW)) {
                // 新id を付与
                i18n.setId(snowflakeIdWorker.nextId());
                // クリエイターidの書き込み
                i18n.setCreatorId(PropConst.SYSTEM_ID);
                // 新規データの収集
                allPageData.getNewPageItemI18n().add(i18n);
            } else if (i18n.getItemStatus().equals(FrontConst.ItemStatus.UPDATE)) {
                // 更新者idの書き込み
                i18n.setUpdaterId(PropConst.SYSTEM_ID);
                // 更新データの収集
                allPageData.getModifyPageItemI18n().add(i18n);
            } else {
                log.warn(MessageConst.ILLEGAL_ITEM_STATUS + ": ",i18n);
                throw new OriginException(ResultType.ILLEGAL_ITEM_STATUS);
            }
        });
        
        // 既存の pageItemResource の場合
        pageRO.getPageItemResourceArr().forEach(resource -> {
            // 子データが新規の場合
            if (resource.getItemStatus().equals(FrontConst.ItemStatus.NEW)) {
                // 関連ファイルがある場合、旧データの削除と関連ファイルの取得
                String fileUrl = filePath.remove(resource.getId());
                // 新id を付与
                resource.setId(snowflakeIdWorker.nextId());
                // 画像データがある場合、関連するファイルのidを更新
                if (fileUrl != null) {
                    // 関連するファイルのurlを書き込む
                    resource.setUrl(fileUrl);
                }
                // クリエイターidの書き込み
                resource.setCreatorId(PropConst.SYSTEM_ID);
                // 新規データの収集
                allPageData.getNewPageItemRes().add(resource);
                // 子データが更新の場合
            } else if (resource.getItemStatus().equals(FrontConst.ItemStatus.UPDATE)) {
                // 更新者idの書き込み
                resource.setUpdaterId(PropConst.SYSTEM_ID);
                // 関連ファイルがある場合、旧データの削除と関連ファイルの取得
                String newFileUrl = filePath.remove(resource.getId());
                // 関連ファイルがある場合、関連するファイルのパスを更新
                if (newFileUrl != null) {
                    // 旧ファイルを臨時ディレクトリに移動
                    File oldFilePath = MyFileUtils.urlToFile(resource.getUrl());
                    File tempOldDir  = new File(UrlConst.TEMP_OLD_DIRECTORY,UrlConst.PAGE_FOLDER);
                    if (!tempOldDir.exists()) {
                        boolean resultOfMkdirs = tempOldDir.mkdirs();
                        if (!resultOfMkdirs) {
                            log.error(MessageConst.FAILED_TO_MAKE_DIRECTORIES);
                            throw new OriginException(ResultType.FAILED_TO_MAKE_DIRECTORIES);
                        }
                    }
                    File tempOldFile = new File(tempOldDir,oldFilePath.getName());
                    // 移動開始
                    boolean moved = oldFilePath.renameTo(tempOldFile);
                    if (!moved) {
                        log.error(MessageConst.FAILED_TO_MOVE_OLD_FILE);
                        throw new OriginException(ResultType.FAILED_TO_MOVE_OLD_FILE);
                    }
                    // urlを更新
                    resource.setUrl(newFileUrl);
                }
                
                // 更新データの収集
                allPageData.getModifyPageItemRes().add(resource);
            } else {
                log.warn(MessageConst.ILLEGAL_ITEM_STATUS + ": ",resource);
                throw new OriginException(ResultType.ILLEGAL_ITEM_STATUS);
            }
        });
        
        // 削除対象の分類
        pageRO.getRemoveDataArr().forEach(target -> {
            switch (target.getUploadCategory()) {
                // pageItemI18n
                case FrontConst.UploadCategory.PAGE_I18N:
                    allPageData.getDeletePageItemI18n().add(target);
                    break;
                // pageItemRes
                case FrontConst.UploadCategory.PAGE_RESOURCE:
                    allPageData.getDeletePageItemRes().add(target);
                    break;
                // pageItem
                case FrontConst.UploadCategory.PAGE:
                    allPageData.getDeletePageItem().add(target);
                    break;
                // 想定外の削除対象
                default:
                    log.warn(MessageConst.ILLEGAL_UPLOAD_CATEGORY,target.getUploadCategory());
                    throw new OriginException(ResultType.ILLEGAL_UPLOAD_CATEGORY);
            }
            
        });
        
        return allPageData;
    }
    
    @Override
    public Map<Long,String> savePageFiles(Map<Long,MultipartFile> fileMap) {
        if (fileMap.isEmpty())
            // ここにMaps.of()などで作成した変更不可なmapを使ってはいけない、上記 assignIdAndCollectData 方法で remove 方法を使うため
            return new HashMap<>();
        
        // ファイルパス保存用
        Map<Long,String> filePath = new HashMap<>();
        
        // 臨時ディレクトリに保存する
        File tempSaveDir = new File(UrlConst.TEMP_NEW_DIRECTORY,UrlConst.PAGE_FOLDER);
        if (!tempSaveDir.exists()) {
            boolean resultOfMkdirs = tempSaveDir.mkdirs();
            if (!resultOfMkdirs) {
                log.error(MessageConst.FAILED_TO_MAKE_DIRECTORIES);
                throw new OriginException(ResultType.FAILED_TO_MAKE_DIRECTORIES);
            }
        }
        // 保存開始
        try {
            for (Map.Entry<Long,MultipartFile> entry : fileMap.entrySet()) {
                // ファイル名をランダム化、拡張子は保留
                String fileName =
                        UUID.randomUUID() + MyFileUtils.getExtension(entry.getValue().getOriginalFilename());
                // ファイルの保存(臨時ディレクトリに)
                File saveFile = new File(tempSaveDir,fileName);
                entry.getValue().transferTo(saveFile);
                // ファイルのURLの作成(実際のurl)
                filePath.put(entry.getKey(),
                        UrlConst.SITE + UrlConst.PAGE_FOLDER + "/" + fileName);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            
            log.warn(MessageConst.FAILED_TO_SAVE_FILES);
            throw new OriginException(ResultType.FAILED_TO_SAVE_FILES);
        }
        
        if (filePath.size() != fileMap.size()) {
            log.warn(MessageConst.FILE_COUNT_INCONSISTENT);
            throw new OriginException(ResultType.FILE_COUNT_INCONSISTENT);
        }
        
        return filePath;
    }
    
    @Override
    public boolean movePageFilesToTempOld(List<RemoveDataRO> targetResList) {
        // 臨時ディレクトリのPath作成
        Path tempOldDir = Paths.get(UrlConst.TEMP_OLD_DIRECTORY,UrlConst.PAGE_FOLDER);
        
        try {
            // ディレクトリが存在しない場合、作成する
            if (!Files.exists(tempOldDir)) {
                Files.createDirectories(tempOldDir);
            }
        } catch (IOException e) {
            log.error(MessageConst.FAILED_TO_MAKE_DIRECTORIES,e);
            throw new OriginException(ResultType.FAILED_TO_MAKE_DIRECTORIES);
        }
        
        for (RemoveDataRO targetRes : targetResList) {
            // 対象が画像、urlも存在する場合
            if (PropConst.ResourceType.IMAGE.equals(targetRes.getResourceType()) && targetRes.getFileUrl() != null && !targetRes.getFileUrl().isEmpty()) {
                // urlの変換
                Path sourcePath = MyFileUtils.urlToFile(targetRes.getFileUrl()).toPath();
                Path targetPath = tempOldDir.resolve(sourcePath.getFileName());
                // 移動開始
                try {
                    // 既存なら上書き
                    Files.move(sourcePath,targetPath,StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    log.warn(MessageConst.FAILED_TO_MOVE_OLD_FILE + " : {}",targetRes.getFileUrl(),e);
                    throw new OriginException(ResultType.FAILED_TO_MOVE_OLD_FILE,targetRes.getFileUrl());
                }
            }
        }
        
        return true;
    }
    
    
    // pageItem の追加（isEmpty()の判断は必要、sql の foreach で空のlistをあげるとエラーになるため）
    @Override
    public boolean addNewPageItemList(List<AdminPageItemDTO> pageItemList) {
        if (pageItemList.isEmpty()) {
            return true;
        }
        int insertedPageItem = adminPageItemMapper.insertPageItem(pageItemList);
        return insertedPageItem == pageItemList.size();
    }
    
    // pageI18n の追加
    @Override
    public boolean addNewPageI18nList(List<AdminPageItemI18nDTO> i18nList) {
        if (i18nList.isEmpty()) {
            return true;
        }
        int insertedPageI18n = adminPageItemMapper.insertPageItemI18n(i18nList);
        return insertedPageI18n == i18nList.size();
    }
    
    // pageRes の追加
    @Override
    public boolean addNewPageResList(List<AdminPageItemResourceDTO> resourceList) {
        if (resourceList.isEmpty()) {
            return true;
        }
        int insertedPageRes = adminPageItemMapper.insertPageItemResource(resourceList);
        return insertedPageRes == resourceList.size();
    }
    
    // pageI18n の更新
    @Override
    public boolean modifyPageI18nList(List<AdminPageItemI18nDTO> pageItemI18nList) {
        if (pageItemI18nList.isEmpty()) {
            return true;
        }
        int updatedPageI18n = adminPageItemMapper.updatePageItemI18n(pageItemI18nList);
        return updatedPageI18n == pageItemI18nList.size();
    }
    
    // pageRes の更新
    @Override
    public boolean modifyPageResList(List<AdminPageItemResourceDTO> pageItemResList) {
        if (pageItemResList.isEmpty()) {
            return true;
        }
        int updatedPageRes = adminPageItemMapper.updatePageItemResource(pageItemResList);
        return updatedPageRes == pageItemResList.size();
    }
    
    @Override
    public boolean deletePageItemListAndChild(List<RemoveDataRO> pageItemList) {
        if (pageItemList.isEmpty()) {
            return true;
        }
        return pageItemList.size() == adminPageItemMapper.deletePageItemAndChild(pageItemList);
    }
    
    @Override
    public boolean deletePageI18nList(List<RemoveDataRO> pageItemI18nList) {
        if (pageItemI18nList.isEmpty()) {
            return true;
        }
        return pageItemI18nList.size() == adminPageItemMapper.deletePageItemI18n(pageItemI18nList);
    }
    
    @Override
    public boolean deletePageResList(List<RemoveDataRO> pageItemResList) {
        if (pageItemResList.isEmpty()) {
            return true;
        }
        return pageItemResList.size() == adminPageItemMapper.deletePageItemResource(pageItemResList);
    }
    
}
