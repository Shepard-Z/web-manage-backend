package com.pots.site.mapper;

import com.pots.site.model.dto.GameDetailDTO;
import com.pots.site.model.dto.GameForCharaDTO;
import com.pots.site.model.dto.GameMainDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameMapper {
    
    List<GameMainDTO> findAllGame(@Param("langCode") String langCode,@Param("resourceType") String resourceType);
    
    GameDetailDTO findGameDetailByGameId(@Param("langCode") String langCode,@Param("gameId") Long gameId);
    
    List<GameForCharaDTO> findAllGameForChara(@Param("langCode") String langCode,
                                              @Param("resourceType") String resourceType);

    List<String> findGameImgListByGameId(@Param("langCode") String langCode,@Param("gameId") Long gameId,
                                         @Param("resourceType") String resourceType);
    
}
