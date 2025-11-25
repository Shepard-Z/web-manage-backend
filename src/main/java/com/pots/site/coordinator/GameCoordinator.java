package com.pots.site.coordinator;

import com.pots.site.model.vo.GameWithCharaVO;
import com.pots.site.model.dto.GameMainDTO;

import java.util.List;

public interface GameCoordinator {
    
    List<GameMainDTO> getAllGame(String langCode);
    
    GameWithCharaVO getGameWithCharaByGameId(String langCode,Long gameId);
    
}
