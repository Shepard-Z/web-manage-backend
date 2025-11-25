package com.pots.site.service;

import com.pots.site.model.vo.PageItemVO;

import java.util.List;

public interface PageItemService {
    
    List<PageItemVO> getPageItemByPageName(String pageName,String langCode);
    
}
