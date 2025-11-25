package com.pots.site.model.vo;

import com.pots.site.model.dto.CharaDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import lombok.Data;

import java.util.List;

@Data
public class CharaWithGameVO {
    
    // 複合型のVOクラスのため、メンバー変数の命名スタイルはフロントエンドの命名規則に従う
    private List<CharaDetailDTO>  charaDetailArr;
    private List<GameForCharaDTO> gameForCharaArr;
    
}
