package com.pots.site.model.vo;


import com.pots.site.model.dto.CharaForGameDTO;
import com.pots.site.model.dto.GameDetailDTO;
import lombok.Data;

import java.util.List;


@Data
public class GameWithCharaVO {
    
    // 複合型のVOクラスのため、メンバー変数の命名はフロントエンドの命名に従う（バックエンドの命名スタイルを使用しない）
    private GameDetailDTO         gameDetail;
    private List<String>          imgUrlArr;
    private List<CharaForGameDTO> charaForGameArr;
    
}
