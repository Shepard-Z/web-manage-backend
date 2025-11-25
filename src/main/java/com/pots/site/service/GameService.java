package com.pots.site.service;

import com.pots.site.model.dto.GameDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.dto.GameMainDTO;

import java.util.List;

public interface GameService {
    
    List<GameMainDTO> getAllGameMainVOList(String langCode,String resourceType);
    
    GameDetailDTO getGameDetailDTOByGameId(String langCode,Long gameId);
    
    // ----------------------------------------------------------------
    List<GameForCharaDTO> getAllGameForCharaDTO(String langCode,String resourceType);
    
    // ----------------------------------------------------------------
    List<String> getGameImageList(String langCode,Long gameId,String resourceType);
    
}
