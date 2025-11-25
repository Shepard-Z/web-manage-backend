package com.pots.site.service.impl;

import com.pots.site.mapper.GameMapper;
import com.pots.site.model.dto.GameDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.dto.GameMainDTO;
import com.pots.site.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    
    public final GameMapper gameMapper;
    
    @Autowired
    public GameServiceImpl(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }
    
    @Override
    public List<GameMainDTO> getAllGameMainVOList(String langCode,String resourceType) {
        return gameMapper.findAllGame(langCode,resourceType);
    }
    
    @Override
    public GameDetailDTO getGameDetailDTOByGameId(String langCode,Long gameId) {
        return gameMapper.findGameDetailByGameId(langCode,gameId);
    }
    
    // ----------------------------------------------------------------
    @Override
    public List<GameForCharaDTO> getAllGameForCharaDTO(String langCode,String resourceType) {
        return gameMapper.findAllGameForChara(langCode,resourceType);
    }
    
    // ----------------------------------------------------------------
    @Override
    public List<String> getGameImageList(String langCode,Long gameId,String resourceType) {
        return gameMapper.findGameImgListByGameId(langCode,gameId,resourceType);
    }
    
}
