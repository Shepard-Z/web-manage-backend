package com.pots.site.mapper;

import com.pots.site.model.dto.PageItemDTO;
import com.pots.site.model.dto.PageItemResourceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PageItemMapper {
    
    List<PageItemDTO> findPageItemByPageName(@Param("pageName") String name,@Param("langCode") String langCode);
    
    List<PageItemResourceDTO> findResourceByPageItemId(@Param("langCode") String langCode,
                                                       @Param("pageItemIdSet") Set<Long> pageItemIdSet,
                                                       @Param("resourceType") String resourceType);
    
    
}
