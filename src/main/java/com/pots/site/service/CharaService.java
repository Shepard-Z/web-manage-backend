package com.pots.site.service;

import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.CharaForGameDTO;
import com.pots.site.model.vo.CharaWithGameVO;

import java.util.List;

public interface CharaService {
    
    List<CharaDetailDTO> getCharaDetailDTOByGameId(String langCode,Long gameId,String resourceType);
    
    // ----------------------------------------------------------------
    List<CharaForGameDTO> getCharaForGameDTOByGameId(String langCode,Long gameId,String resourceType);
    
}
