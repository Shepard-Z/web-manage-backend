package com.pots.admin.interceptor;

import com.pots.common.constant.MessageConst;
import com.pots.common.constant.PropConst;
import com.pots.common.model.ResultType;
import com.pots.common.model.TokenStatus;
import com.pots.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
public class AdminTokenInterceptor implements HandlerInterceptor {
    
    private final JwtUtils jwtUtils;
    
    public AdminTokenInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws IOException {
        // authHeader と authHeader.startsWith の "Authorization" と "Bearer " は全部フロント側で定義した定数であり、一致しないと改ざんされた恐れがある
        String authHeader = request.getHeader("Auth");
        if (authHeader == null || !authHeader.startsWith("BFM ")) {
            log.error(MessageConst.INVALID_TOKEN);
            sendError(response,ResultType.TOKEN_INVALID);
            return false;
        }
        
        String      token  = authHeader.substring(7);
        TokenStatus status = jwtUtils.verifyToken(token);
        return switch (status) {
            case VALID -> {
                Long   userId   = jwtUtils.getUserIdFromToken(token);
                String userName = jwtUtils.getUserNameFromToken(token);
                if (userId == null) {
                    log.warn(MessageConst.FAILED_TO_VERIFY_USER_ID);
                    sendError(response,ResultType.FAILED_TO_VERIFY_USER_ID);
                    yield false;
                }
                request.setAttribute(PropConst.REQUEST_ATTRIBUTE_ID,userId);
                request.setAttribute(PropConst.REQUEST_ATTRIBUTE_NAME,userName);
                yield true;
            }
            case EXPIRED -> {
                sendError(response,ResultType.TOKEN_EXPIRED);
                yield false;
            }
            default -> {
                log.error(MessageConst.INVALID_TOKEN);
                sendError(response,ResultType.TOKEN_INVALID);
                yield false;
            }
        };
    }
    
    private void sendError(HttpServletResponse response,ResultType resultType) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(210);
        String json = String.format("{\"status\":%d,\"message\":\"%s\"}",resultType.status,resultType.message);
        response.getWriter().write(json);
    }
    
}