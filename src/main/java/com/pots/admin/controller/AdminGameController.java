package com.pots.admin.controller;

import com.pots.admin.coordinator.AdminGameCoordinator;
import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;
import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/game")
public class AdminGameController {
    
    @Autowired
    AdminGameCoordinator adminGameCoordinator;
    
    @GetMapping()
    public Result<?> sendGameSimpleData(HttpServletRequest request) {
        // リクエストのユーザーidをチェック
        ResultType resultTypeForCheck = adminGameCoordinator.checkUser(request);
        if (resultTypeForCheck != null) {
            return Result.failed(resultTypeForCheck);
        }
        
        List<AdminGameSimpleVO> gameSimpleVOList = adminGameCoordinator.getAllGameSimpleData();
        if (gameSimpleVOList.isEmpty()) {
            return Result.success(ResultType.NO_DATA,List.of());
        }
        
        return Result.success(gameSimpleVOList);
    }
    
    @GetMapping("/{gameId}")
    public Result<?> sendGameDetailData(@PathVariable String gameId,HttpServletRequest request) {
        // リクエストのユーザーidをチェック
        ResultType resultTypeForCheck = adminGameCoordinator.checkUser(request);
        if (resultTypeForCheck != null) {
            return Result.failed(resultTypeForCheck);
        }
        
        AdminGameDetailVO gameDetailVO = adminGameCoordinator.getGameDetailById(Long.parseLong(gameId));
        if (gameDetailVO == null) {
            return Result.success(ResultType.NO_DATA,List.of());
        }
        
        return Result.success(gameDetailVO);
    }
    
    
    
    
}
