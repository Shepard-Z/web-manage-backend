package com.pots.admin.coordinator.impl;

import com.pots.admin.coordinator.AdminGameCoordinator;
import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;
import com.pots.admin.service.AdminGameService;
import com.pots.admin.service.AdminUserService;
import com.pots.common.constant.PropConst;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminGameCoordinatorImpl implements AdminGameCoordinator {
    
    @Autowired
    AdminGameService adminGameService;
    @Autowired
    AdminUserService adminUserService;
    
    @Override
    public List<AdminGameSimpleVO> getAllGameSimpleData() {
        return adminGameService.getAllGameSimpleList();
    }
    
    @Override
    public AdminGameDetailVO getGameDetailById(Long gameId) {
        AdminGameDetailVO gameDetailVO = adminGameService.getGameById(gameId);
        if (gameDetailVO == null) {
            return null;
        }
        // 対象 game の多言語データを取得
        gameDetailVO.setI18nArr(adminGameService.getAllGameI18nByGameId(gameId));
        // 対象 game の資源データを取得
        gameDetailVO.setResArr(adminGameService.getAllGameResImgByGameId(gameId,PropConst.ResourceType.IMAGE));
        
        return gameDetailVO;
        
    }
    
    @Override
    public ResultType checkUser(HttpServletRequest request) {
        return adminUserService.checkUser(request);
    }
    
}
