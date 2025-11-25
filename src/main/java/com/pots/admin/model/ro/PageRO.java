package com.pots.admin.model.ro;

import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import com.pots.admin.model.vo.AdminPageItemVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageRO {
    
    private List<AdminPageItemVO>          pageItemArr = new ArrayList<>();
    private List<AdminPageItemI18nDTO>     pageItemI18nArr = new ArrayList<>();
    private List<AdminPageItemResourceDTO> pageItemResourceArr = new ArrayList<>();
    private List<RemoveDataRO>             removeDataArr = new ArrayList<>();
    
}
