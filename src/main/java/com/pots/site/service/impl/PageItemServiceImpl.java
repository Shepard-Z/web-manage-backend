package com.pots.site.service.impl;

import com.pots.site.mapper.PageItemMapper;
import com.pots.site.model.dto.PageItemDTO;
import com.pots.site.model.dto.PageItemResourceDTO;
import com.pots.site.model.vo.PageItemVO;
import com.pots.site.service.PageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PageItemServiceImpl implements PageItemService {
    
    @Autowired
    private PageItemMapper pageItemMapper;
    
    @Override
    public List<PageItemVO> getPageItemByPageName(String pageName,String langCode) {
        
        // pageのアイテムを全部取得
        List<PageItemDTO> pageItemDTOList = pageItemMapper.findPageItemByPageName(pageName,langCode);
        
        // 対象データがない場合、後継処理不要
        if (pageItemDTOList.isEmpty()) {
            return List.of();
        }
        
        // pageのアイテムのidを抽出(setで重複削除,)
        Set<Long> pageItemIdSet = pageItemDTOList.stream()
                .map(PageItemDTO::getId)
                .collect(Collectors.toSet());
        
        // 画像を抽出
        List<PageItemResourceDTO> itemResourceDTOList = pageItemMapper.findResourceByPageItemId(langCode,pageItemIdSet,"image");
        
        // 対象データがない場合、既存のデータを返す,imgArrを空配列で返す
        if (itemResourceDTOList.isEmpty()) {
            // VOを作成
            return pageItemDTOList.stream()
                    .map(dto -> PageItemVO.builder().item(dto).imgArr(List.of()).build())
                    .collect(Collectors.toList());
            
        }
        
        // vo作成時の計算量を下げるため、画像DTOリストをMapにする -- 計算量: O(m × n) -> O(n + m)
        Function<PageItemResourceDTO,Long>   keyMapper   = PageItemResourceDTO::getPageItemId;
        Function<PageItemResourceDTO,String> valueMapper = PageItemResourceDTO::getUrl;
        
        Map<Long,List<String>> imageMap = itemResourceDTOList.stream()
                .collect(Collectors.groupingBy(keyMapper,Collectors.mapping(valueMapper,Collectors.toList())));
        
        // VOを作成
        return pageItemDTOList.stream()
                .map(dto ->
                        PageItemVO.builder()
                                .item(dto)
                                .imgArr(imageMap.getOrDefault(dto.getId(),List.of()))
                                .build())
                .collect(Collectors.toList());
    }
    
    
}