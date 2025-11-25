package com.pots.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pots.admin.coordinator.AdminPageItemCoordinator;
import com.pots.admin.coordinator.FullUpdateCoordinator;
import com.pots.admin.model.ro.PageRO;
import com.pots.admin.model.vo.AdminPageItemVO;
import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import com.pots.common.utils.MyFileUtils;
import com.pots.common.utils.OriginException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin/page")
public class AdminPageItemController {
    
    
    @Autowired
    private FullUpdateCoordinator    fullUpdateCoordinator;
    @Autowired
    private AdminPageItemCoordinator adminPageItemCoordinator;
    
    // ページデータ
    @GetMapping("/{pageName}")
    public Result<List<AdminPageItemVO>> sendPageData(@PathVariable String pageName,HttpServletRequest request) {
        // リクエストのユーザーidをチェック
        ResultType resultTypeForCheck = adminPageItemCoordinator.checkUser(request);
        if (resultTypeForCheck != null) {
            return Result.failed(resultTypeForCheck);
        }
        
        List<AdminPageItemVO> pageItemVOList = adminPageItemCoordinator.getAllPageItem(pageName);
        // データがない場合、失敗ではない(ただ一つも存在しないだけ、滅多にない)
        if (pageItemVOList.isEmpty()) {
            return Result.success(ResultType.NO_DATA,List.of());
        }
        
        return Result.success(pageItemVOList);
    }
    
    @PostMapping("/{pageName}/full")
    public Result<?> pageFullUpdate(@PathVariable String pageName,
                                    @RequestPart("text") String textData,
                                    MultipartHttpServletRequest request) {
        // リクエストのユーザーidをチェック
        ResultType resultTypeForCheck = fullUpdateCoordinator.checkUser(request);
        if (resultTypeForCheck != null) {
            return Result.failed(resultTypeForCheck);
        }
        
        PageRO pageRO = new PageRO();
        // テキストデータを解析する
        try {
            pageRO = new ObjectMapper().readValue(textData,PageRO.class);
        } catch (Exception e) {
            log.warn("テキストデータの解析が失敗した: {}",textData);
            return Result.failed(ResultType.REQUEST_PARSING_FAILED);
        }
        
        // ファイルデータを収集
        Map<Long,MultipartFile> filesMap = new HashMap<>();
        for (Map.Entry<String,MultipartFile> entry : request.getFileMap().entrySet()) {
            String        formName = entry.getKey();
            MultipartFile file     = entry.getValue();
            // 予想外のフォームファイル名が出た場合、直接return
            if (!formName.startsWith("files_")) {
                log.warn("イリーガルのフォーム名: {}",formName);
                return Result.failed(ResultType.ILLEGAL_FILE_FORM_NAME);
            }
            try {
                // フォーム名である files_XXX の files_をカットし、idだけ残す
                filesMap.put(Long.parseLong(formName.substring(6)),file);
            } catch (NumberFormatException e) {
                log.warn("ファイルのフォーム名の解析が失敗した: {}",formName);
                return Result.failed(ResultType.REQUEST_PARSING_FAILED);
            }
        }
        
        // 更新処理開始
        ResultType resultType;
        try {
            resultType = fullUpdateCoordinator.pageItem(pageRO,filesMap);
            // 手動で投げ出された異常をキャッチした場合
        } catch (OriginException e) {
            // ファイルの復旧
            try {
                MyFileUtils.restoreFiles();
            } catch (Exception restoreEx) {
                log.error(restoreEx.getMessage());
            }
            return Result.failed(e.getResultType(),e.getOriginMessage());
        }catch(Exception e){
            log.warn("資源更新失敗");
            return Result.failed(ResultType.FAILED,e.getMessage());
        }
        
        
        return Result.success(List.of());
        
    }
    
}
