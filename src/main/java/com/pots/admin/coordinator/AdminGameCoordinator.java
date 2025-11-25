package com.pots.admin.coordinator;


import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminGameCoordinator {
    
    List<AdminGameSimpleVO> getAllGameSimpleData();
    
    AdminGameDetailVO getGameDetailById(Long gameId);
    
    ResultType checkUser(HttpServletRequest request);
    
}
