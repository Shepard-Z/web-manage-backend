package com.pots.site.model.vo;

import com.pots.site.model.dto.PageItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PageItemVO {
    
    private PageItemDTO  item;
    private List<String> imgArr;
    
}
