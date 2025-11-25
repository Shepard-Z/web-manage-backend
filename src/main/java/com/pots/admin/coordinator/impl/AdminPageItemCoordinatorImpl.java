package com.pots.admin.coordinator.impl;

import com.pots.admin.coordinator.AdminPageItemCoordinator;
import com.pots.admin.model.vo.AdminPageItemVO;
import com.pots.admin.service.AdminPageItemService;
import com.pots.admin.service.AdminUserService;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPageItemCoordinatorImpl implements AdminPageItemCoordinator {
    
    @Autowired
    private AdminPageItemService adminPageItemService;
    @Autowired
    AdminUserService adminUserService;
    
    
    @Override
    public List<AdminPageItemVO> getAllPageItem(String pageName) {
        return adminPageItemService.getPageItemByPageName(pageName);
    }
    
    @Override
    public ResultType checkUser(HttpServletRequest request) {
        return adminUserService.checkUser(request);
    }
    
}
