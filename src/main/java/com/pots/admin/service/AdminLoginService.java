package com.pots.admin.service;

import com.pots.admin.model.vo.AdminUserVO;


public interface AdminLoginService {
    
    AdminUserVO getAdminByName(String username);
    
}
