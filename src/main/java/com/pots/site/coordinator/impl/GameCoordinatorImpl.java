package com.pots.site.coordinator.impl;

import com.pots.common.constant.PropConst;
import com.pots.site.coordinator.GameCoordinator;
import com.pots.site.model.dto.GameMainDTO;
import com.pots.site.model.vo.GameWithCharaVO;
import com.pots.site.service.CharaService;
import com.pots.site.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class GameCoordinatorImpl implements GameCoordinator {
    
    private final GameService     gameService;
    private final CharaService    charaService;
    
    @Autowired
    public GameCoordinatorImpl(GameService gameService,CharaService charaService) {
        this.gameService = gameService;
        this.charaService = charaService;
    }
    
    @Override
    public List<GameMainDTO> getAllGame(String langCode) {
        List<GameMainDTO> gameMainDTOList = gameService.getAllGameMainVOList(langCode,PropConst.ResourceType.IMAGE);
        // releaseDate により、逆順ソート
        gameMainDTOList.sort(Comparator.comparing(GameMainDTO::getReleaseDate,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return gameMainDTOList;
    }
    
    @Override
    public GameWithCharaVO getGameWithCharaByGameId(String langCode,Long gameId) {
        GameWithCharaVO gameWithCharaVO = new GameWithCharaVO();
        
        // ゲーム詳細情報を取得
        gameWithCharaVO.setGameDetail(gameService.getGameDetailDTOByGameId(langCode,gameId));
        // gameId によるゲーム詳細の抽出が失敗した場合、リスポンスが失敗になるため、後継処理不要
        if (gameWithCharaVO.getGameDetail() == null) {
            return null;
        }
        
        // ゲーム画像URLを取得,画像の抽出が失敗した場合、リスポンスが失敗にならないため、通常にreturn
        gameWithCharaVO.setImgUrlArr(gameService.getGameImageList(langCode,gameId,PropConst.ResourceType.IMAGE));
        
        // gameId による関連キャラクターの抽出が失敗した場合、リスポンスが失敗にならないため、通常にreturn
        gameWithCharaVO.setCharaForGameArr(charaService.getCharaForGameDTOByGameId(langCode,gameId,PropConst.ResourceType.IMAGE));
        return gameWithCharaVO;
    }
    
    
}
