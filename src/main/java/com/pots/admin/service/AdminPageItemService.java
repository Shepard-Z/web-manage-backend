package com.pots.admin.service;


import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.vo.AdminPageItemVO;

import java.util.List;

public interface AdminPageItemService {
    
    List<AdminPageItemVO> getPageItemByPageName(String pageName);
    
    List<AdminPageItemResourceDTO> getPageItemResByItemId(List<Long> pageItemIdList);
    
}
