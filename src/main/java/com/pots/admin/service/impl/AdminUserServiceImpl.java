package com.pots.admin.service.impl;

import com.pots.admin.mapper.AdminUserMapper;
import com.pots.admin.service.AdminUserService;
import com.pots.common.constant.MessageConst;
import com.pots.common.constant.PropConst;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {
    
    @Autowired
    AdminUserMapper userMapper;
    
    @Override
    public ResultType checkUser(HttpServletRequest request) {
        // リクエストのユーザーidをチェック
        Object idObj   = request.getAttribute(PropConst.REQUEST_ATTRIBUTE_ID);
        Object nameObj = request.getAttribute(PropConst.REQUEST_ATTRIBUTE_NAME);
        // 解析が失敗した場合
        if (idObj == null || nameObj == null) {
            log.error(MessageConst.FAILED_TO_VERIFY_USER_ID);
            return ResultType.FAILED_TO_VERIFY_USER_ID;
        }
        Long   id   = (idObj instanceof Long) ? (Long) idObj : Long.parseLong(idObj.toString());
        String name = nameObj.toString();
        // ユーザーチェックが失敗した場合
        if(userMapper.checkAdminByIdName(id,name) == null){
            log.error(MessageConst.ILLEGAL_USER,String.format("userId: %d, userName: %s",id,nameObj));
            return ResultType.ILLEGAL_USER;
        }
        return null;
    }
    
}
