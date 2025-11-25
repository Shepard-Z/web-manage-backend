package com.pots.site.coordinator;

import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.vo.CharaWithGameVO;

import java.util.List;

public interface CharaCoordinator {
    
    CharaWithGameVO getDefaultCharaWithAllGame(String langCode);
    
    List<GameForCharaDTO> getAllGame(String langCode);
    
    List<CharaDetailDTO> getCharaListByGameId(String langCode,Long gameId);
    
}
