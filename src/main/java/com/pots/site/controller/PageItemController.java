package com.pots.site.controller;

import com.pots.common.model.Result;
import com.pots.common.model.ResultType;
import com.pots.common.utils.Inspector;
import com.pots.common.utils.LangCheckResult;
import com.pots.site.model.vo.PageItemVO;
import com.pots.site.service.PageItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PageItemController {
    
    @Autowired
    private PageItemService pageItemService;
    
    // ページデータ
    @GetMapping("/page/{pageName}")
    public Result<List<PageItemVO>> sendHomePage(@PathVariable String pageName,HttpServletRequest request) {
        // パラメータ検証
        LangCheckResult  lcr      = Inspector.languageCheck(request);
        List<PageItemVO> pageData = pageItemService.getPageItemByPageName(pageName,lcr.langCode());
        // データがない場合、失敗ではない(滅多にない)
        if (pageData.isEmpty()) {
            // LangCheckResultの ResultType を使用せず、ResultType.NO_DATA を優先する
            return Result.success(ResultType.NO_DATA,List.of());
        }
        return Result.success(lcr.resultType(),pageData);
    }
    
}
