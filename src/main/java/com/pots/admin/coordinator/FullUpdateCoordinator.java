package com.pots.admin.coordinator;

import com.pots.admin.model.ro.PageRO;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FullUpdateCoordinator {
    
    ResultType pageItem(PageRO pageRO,Map<Long,MultipartFile> filesMap);
    
    ResultType game();
    
    ResultType chara();
    
    ResultType checkUser(HttpServletRequest request);
    
}
