package com.pots.admin.controller;

import com.pots.admin.model.vo.AdminUserVO;
import com.pots.admin.service.AdminLoginService;
import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import com.pots.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/admin/login-swim")
@RestController
public class AdminLogin {
    
    private final JwtUtils jwtUtils;
    @Autowired
    AdminLoginService adminLoginService;
    
    
    public AdminLogin(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    
    @PostMapping()
    public Result<AdminUserVO> login(@RequestBody Map<String,String> params,HttpServletRequest request) {
        String  username = params.get("username");
        String  password = params.get("password");
        
        AdminUserVO user = adminLoginService.getAdminByName(username);
        // ユーザーが存在しない場合
        if (user == null) {
            return Result.failed(ResultType.USER_NOT_FOUND);
        }
        // パスワードが間違った場合
        if (!BCrypt.checkpw(password,user.getPasswordHash())) {
            return Result.failed(ResultType.INCORRECT_PASSWORD);
        }
        // パスワードをクリア
        user.setPasswordHash(null);
        // token 生成
        user.setToken(jwtUtils.generateToken(user.getId(),user.getName()));
        
        return Result.success(ResultType.SUCCESS,user);
    }
    
}
