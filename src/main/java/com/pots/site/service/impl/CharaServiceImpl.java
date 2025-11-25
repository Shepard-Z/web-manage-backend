package com.pots.site.service.impl;

import com.pots.site.mapper.CharaMapper;
import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.CharaForGameDTO;
import com.pots.site.service.CharaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharaServiceImpl implements CharaService {
    
    public final CharaMapper charaMapper;
    
    @Autowired
    public CharaServiceImpl(CharaMapper charaMapper) {
        this.charaMapper = charaMapper;
    }
    
    @Override
    public List<CharaDetailDTO> getCharaDetailDTOByGameId(String langCode,Long gameId,String resourceType) {
        return charaMapper.findCharaDetailByGameId(langCode,gameId,resourceType);
    }
    
    // ----------------------------------------------------------------
    @Override
    public List<CharaForGameDTO> getCharaForGameDTOByGameId(String langCode,Long gameId,String resourceType) {
        return charaMapper.findCharaForGameByGameId(langCode,gameId,resourceType);
    }
    
}
