package com.pots.admin.service;


import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;

public interface AdminUserService {
    
    ResultType checkUser(HttpServletRequest request);
    
}
