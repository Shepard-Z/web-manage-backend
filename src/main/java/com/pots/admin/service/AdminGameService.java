package com.pots.admin.service;


import com.pots.admin.model.dto.AdminGameI18nDTO;
import com.pots.admin.model.dto.AdminGameResDTO;
import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;

import java.util.List;

public interface AdminGameService {
    
    List<AdminGameSimpleVO> getAllGameSimpleList();
    
    AdminGameDetailVO getGameById(Long id);
    
    List<AdminGameI18nDTO> getAllGameI18nByGameId(Long gameId);
    
    List<AdminGameResDTO> getAllGameResImgByGameId(Long gameId,String resourceType);
    
}
