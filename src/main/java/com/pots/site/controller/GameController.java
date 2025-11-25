package com.pots.site.controller;


import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import com.pots.common.utils.Inspector;
import com.pots.common.utils.LangCheckResult;
import com.pots.site.coordinator.GameCoordinator;
import com.pots.site.model.vo.GameWithCharaVO;
import com.pots.site.model.dto.GameMainDTO;
import com.pots.site.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GameController {
    
    @Autowired
    GameCoordinator gameCoordinator;
    
    @GetMapping("/game")
    public Result<List<GameMainDTO>> sendAllGame(HttpServletRequest request) {
        
        LangCheckResult   lcr             = Inspector.languageCheck(request);
        List<GameMainDTO> gameMainDTOList = gameCoordinator.getAllGame(lcr.langCode());
        // データがない場合、失敗ではない(ただゲームが一つも存在しないだけ、滅多にない)
        if (gameMainDTOList.isEmpty()) {
            // LangCheckResultの ResultType を使用せず、ResultType.NO_DATA を優先する
            return Result.success(ResultType.NO_DATA,List.of());
        }
        return Result.success(lcr.resultType(),gameMainDTOList);
    }
    
    @GetMapping("/game/{gameId}")
    public Result<GameWithCharaVO> sendGameDetail(@PathVariable Long gameId,HttpServletRequest request) {
        LangCheckResult lcr             = Inspector.languageCheck(request);
        GameWithCharaVO gameWithCharaVO = gameCoordinator.getGameWithCharaByGameId(lcr.langCode(),gameId);
        // idによる検索でデータがない場合、失敗になる
        if (gameWithCharaVO == null) {
            // LangCheckResult の ResultType を使用せず、ResultType.DATA_NOT_FOUND を優先する
            return Result.success(ResultType.DATA_NOT_FOUND,null);
        }
        return Result.success(lcr.resultType(),gameWithCharaVO);
    }
    
}
