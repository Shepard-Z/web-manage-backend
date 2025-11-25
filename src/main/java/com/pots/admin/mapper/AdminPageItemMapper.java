package com.pots.admin.mapper;

import com.pots.admin.model.dto.AdminPageItemAllDTO;
import com.pots.admin.model.dto.AdminPageItemDTO;
import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.ro.RemoveDataRO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminPageItemMapper {
    
    // resourceType が null の場合、この条件は無される
    List<AdminPageItemAllDTO> findAllItemByPageName(@Param("pageName") String pageName,
                                                    @Param("resourceType") String resourceType);
    
    List<AdminPageItemResourceDTO> findPageItemResByItemId(@Param("pageItemIdList") List<Long> pageItemIdList);
    
    int insertPageItem(@Param("pageItemList") List<AdminPageItemDTO> pageItemList);
    
    int insertPageItemI18n(@Param("pageItemI18nList") List<AdminPageItemI18nDTO> pageItemI18nList);
    
    int insertPageItemResource(@Param("pageItemResList") List<AdminPageItemResourceDTO> pageItemResList);
    
    int updatePageItemI18n(@Param("pageItemI18nList") List<AdminPageItemI18nDTO> pageItemI18nList);
    
    int updatePageItemResource(@Param("pageItemResList") List<AdminPageItemResourceDTO> pageItemResList);
    
    int deletePageItemAndChild(@Param("pageItemList") List<RemoveDataRO> pageItemList);
    
    int deletePageItemI18n(@Param("pageItemI18nList") List<RemoveDataRO> pageItemI18nList);
    
    int deletePageItemResource(@Param("pageItemResList") List<RemoveDataRO> pageItemResList);
    
}
