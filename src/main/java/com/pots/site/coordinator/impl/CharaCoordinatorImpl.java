package com.pots.site.coordinator.impl;

import com.pots.common.constant.PropConst;
import com.pots.site.coordinator.CharaCoordinator;
import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.vo.CharaWithGameVO;
import com.pots.site.service.CharaService;
import com.pots.site.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharaCoordinatorImpl implements CharaCoordinator {
    
    private final CharaService charaService;
    private final GameService  gameService;
    
    @Autowired
    public CharaCoordinatorImpl(CharaService charaService,GameService gameService) {
        this.charaService = charaService;
        this.gameService = gameService;
    }
    
    
    @Override
    public CharaWithGameVO getDefaultCharaWithAllGame(String langCode) {
        CharaWithGameVO charaWithGameVO = new CharaWithGameVO();
        
        // 全てのゲームの簡略情報を抽出
        List<GameForCharaDTO> gameForCharaDTOList = gameService.getAllGameForCharaDTO(langCode,PropConst.ResourceType.IMAGE);
        // 抽出が失敗した場合、後継処理不要
        if (gameForCharaDTOList.isEmpty()) {
            return null;
        }
        charaWithGameVO.setGameForCharaArr(gameForCharaDTOList);
        
        // sortOrder = 1 のデフォルトゲームを取得
        Long defaultGameId = gameForCharaDTOList.stream()
                .filter(game -> game.getSortOrder() == 1)
                .map(GameForCharaDTO::getId)
                .findFirst()
                // 万が一 sortOrder = 1 のゲームが存在しない場合、最初のゲームをデフォルトにする
                .orElse(gameForCharaDTOList.get(0).getId());
        
        // デフォルトゲームの関連キャラを抽出
        charaWithGameVO.setCharaDetailArr(charaService.getCharaDetailDTOByGameId(langCode,defaultGameId,
                PropConst.ResourceType.IMAGE));
        // 抽出が失敗した場合、後継処理不要
        if (charaWithGameVO.getCharaDetailArr().isEmpty()) {
            return null;
        }
        return charaWithGameVO;
    }
    
    @Override
    public List<GameForCharaDTO> getAllGame(String langCode) {
        return gameService.getAllGameForCharaDTO(langCode,PropConst.ResourceType.IMAGE);
    }
    
    @Override
    public List<CharaDetailDTO> getCharaListByGameId(String langCode,Long gameId) {
        return charaService.getCharaDetailDTOByGameId(langCode,gameId,PropConst.ResourceType.IMAGE);
    }
    
}
