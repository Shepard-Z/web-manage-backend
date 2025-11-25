package com.pots.admin.service.impl;

import com.pots.admin.mapper.AdminUserMapper;
import com.pots.admin.model.vo.AdminUserVO;
import com.pots.admin.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    
    @Autowired
    AdminUserMapper userMapper;
    
    @Override
    public AdminUserVO getAdminByName(String username) {
        
        return userMapper.findAdminByUsername(username);
    }
    
}
