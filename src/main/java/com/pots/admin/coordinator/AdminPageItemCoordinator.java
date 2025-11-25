package com.pots.admin.coordinator;

import com.pots.admin.model.vo.AdminPageItemVO;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminPageItemCoordinator {
    
    List<AdminPageItemVO> getAllPageItem(String pageName);
    
    ResultType checkUser(HttpServletRequest request);
    
}
