package com.pots.admin.service.impl;

import com.pots.admin.mapper.AdminGameMapper;
import com.pots.admin.model.dto.AdminGameI18nDTO;
import com.pots.admin.model.dto.AdminGameResDTO;
import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;
import com.pots.admin.service.AdminGameService;
import com.pots.common.constant.PropConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminGameServiceImpl implements AdminGameService {
    
    @Autowired
    AdminGameMapper adminGameMapper;
    
    @Override
    public List<AdminGameSimpleVO> getAllGameSimpleList() {
        return adminGameMapper.findAllGameByLangCode(PropConst.DEFAULT_LANGUAGE,PropConst.ResourceType.IMAGE);
    }
    
    @Override
    public AdminGameDetailVO getGameById(Long id) {
        return adminGameMapper.findGameById(id);
    }
    
    @Override
    public List<AdminGameI18nDTO> getAllGameI18nByGameId(Long gameId) {
        return adminGameMapper.findAllGameI18nByGameId(gameId);
    }
    
    @Override
    public List<AdminGameResDTO> getAllGameResImgByGameId(Long gameId,String resourceType) {
        return adminGameMapper.findAllGameResImgByGameId(gameId,resourceType);
    }
    
}
