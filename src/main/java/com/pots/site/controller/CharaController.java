package com.pots.site.controller;

import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import com.pots.common.utils.Inspector;
import com.pots.common.utils.LangCheckResult;
import com.pots.site.coordinator.CharaCoordinator;
import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.vo.CharaWithGameVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CharaController {
    
    @Autowired
    CharaCoordinator charaCoordinator;
    
    @GetMapping("/chara")
    public Result<CharaWithGameVO> sendCharaListWithAllGame(HttpServletRequest request) {
        LangCheckResult lcr             = Inspector.languageCheck(request);
        CharaWithGameVO charaWithGameVO = charaCoordinator.getDefaultCharaWithAllGame(lcr.langCode());
        // データがない場合、失敗ではない(ただデータがないだけ、滅多にない)
        if (charaWithGameVO == null) {
            // LangCheckResultの ResultType を使用せず、ResultType.NO_DATA を優先する
            return Result.success(ResultType.NO_DATA,null);
        }
        return Result.success(lcr.resultType(),charaWithGameVO);
    }
    
    @GetMapping("/chara/games")
    public Result<List<GameForCharaDTO>> sendAllGame(HttpServletRequest request) {
        LangCheckResult       lcr      = Inspector.languageCheck(request);
        List<GameForCharaDTO> gameList = charaCoordinator.getAllGame(lcr.langCode());
        // データがない場合、失敗ではない(ただデータがないだけ、滅多にない)
        if (gameList.isEmpty()) {
            // LangCheckResultの ResultType を使用せず、ResultType.NO_DATA を優先する
            return Result.success(ResultType.NO_DATA,null);
        }
        return Result.success(gameList);
    }
    
    @GetMapping("/chara/{gameId}")
    public Result<List<CharaDetailDTO>> sendCharaListInGame(@PathVariable Long gameId,HttpServletRequest request) {
        LangCheckResult      lcr       = Inspector.languageCheck(request);
        List<CharaDetailDTO> charaList = charaCoordinator.getCharaListByGameId(lcr.langCode(),gameId);
        // データがない場合、失敗ではない(ただデータがないだけ、滅多にない)
        if (charaList.isEmpty()) {
            // LangCheckResultの ResultType を使用せず、ResultType.NO_DATA を優先する
            return Result.success(ResultType.NO_DATA,List.of());
        }
        return Result.success(charaList);
    }
    
}
