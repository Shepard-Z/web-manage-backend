package com.pots.admin.service.impl;

import com.pots.admin.mapper.AdminPageItemMapper;
import com.pots.admin.model.dto.AdminPageItemAllDTO;
import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.vo.AdminPageItemVO;
import com.pots.admin.service.AdminPageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminPageItemServiceImpl implements AdminPageItemService {
    
    @Autowired
    AdminPageItemMapper adminPageItemMapper;
    
    @Override
    public List<AdminPageItemVO> getPageItemByPageName(String pageName) {
        
        // デカルト積データを抽出(データベースへのアクセス回数を制御するため)
        List<AdminPageItemAllDTO> adminPageItemAllDTOs = adminPageItemMapper.findAllItemByPageName(pageName,null);
        if (adminPageItemAllDTOs.isEmpty()) {
            return List.of();
        }
        
        // 全部のデータをidでグループ分け
        Map<Long,List<AdminPageItemAllDTO>> groupedByIdMap = adminPageItemAllDTOs.stream()
                .collect(Collectors.groupingBy(AdminPageItemAllDTO::getId));
        
        // 結果リストにセット
        return groupedByIdMap.entrySet().stream().map(entry -> {
            // 一つのidに属するデータリスト
            List<AdminPageItemAllDTO> groupList = entry.getValue();
            
            // 主テーブルデータ
            AdminPageItemAllDTO mainDataDTO = entry.getValue().get(0);
            // 主テーブルデータを格納
            AdminPageItemVO resultItemVO = new AdminPageItemVO();
            resultItemVO.setId(entry.getKey());
            resultItemVO.setPageName(mainDataDTO.getPageName());
            resultItemVO.setItemCategory(mainDataDTO.getItemCategory());
            resultItemVO.setIsActive(mainDataDTO.getIsActive());
            resultItemVO.setCreatorId(mainDataDTO.getCreatorId());
            resultItemVO.setCreateTime(mainDataDTO.getCreateTime());
            resultItemVO.setUpdaterId(mainDataDTO.getUpdaterId());
            resultItemVO.setUpdateTime(mainDataDTO.getUpdateTime());
            
            // 多言語テーブルデータを langCode で重複削除(langCode とデータの関係: 1->1)
            Map<String,AdminPageItemI18nDTO> i18nDTOMap = groupList.stream()
                    // Id は null のデカルト積データを排除
                    .filter(i18n -> i18n.getI18nId() != null)
                    .collect(Collectors.toMap(
                            // key
                            AdminPageItemAllDTO::getLangCode,
                            // value
                            i8nItem -> AdminPageItemI18nDTO.builder()
                                    .id(i8nItem.getI18nId())
                                    .pageItemId(mainDataDTO.getId())
                                    .langCode(i8nItem.getLangCode())
                                    .title(i8nItem.getTitle())
                                    .subtitle(i8nItem.getSubTitle())
                                    .introduction(i8nItem.getIntroduction())
                                    .build(),
                            // keyが重複した時の処理
                            (oldValue,newValue) -> oldValue
                    ));
            // 多言語テーブルデータを格納
            resultItemVO.setI18nArr(new ArrayList<>(i18nDTOMap.values()));
            
            // 資源テーブルデータを resLangCode で重複削除( デカルト積データのため distinct() で重複削除するのは合理的 )
            List<AdminPageItemResourceDTO> resDTOList = groupList.stream()
                    // Id は null のデカルト積データを排除
                    .filter(res -> res.getResId() != null)
                    .map(
                            resItem -> AdminPageItemResourceDTO.builder()
                                    .id(resItem.getResId())
                                    .pageItemId(mainDataDTO.getId())
                                    .resourceType(resItem.getResourceType())
                                    .resLangCode(resItem.getResLangCode())
                                    .resSortOrder(resItem.getResSortOrder())
                                    .url(resItem.getUrl())
                                    .build()
                    ).distinct().toList();
            // 資源テーブルデータを格納
            resultItemVO.setResourceArr(new ArrayList<>(resDTOList));
            
            return resultItemVO;
        }).toList();
        
    }
    
    @Override
    public List<AdminPageItemResourceDTO> getPageItemResByItemId(List<Long> pageItemIdList) {
        if (pageItemIdList.isEmpty()) {
            return List.of();
        }
        return adminPageItemMapper.findPageItemResByItemId(pageItemIdList);
    }
    
}
