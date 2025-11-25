package com.pots.site.mapper;

import com.pots.site.model.dto.CharaForGameDTO;
import com.pots.site.model.dto.CharaDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CharaMapper {
    
    List<CharaDetailDTO> findCharaDetailByGameId(@Param("langCode") String langCode,@Param("gameId") Long gameId,@Param("resourceType")String resourceType);
    
    List<CharaForGameDTO> findCharaForGameByGameId(@Param("langCode") String langCode,@Param("gameId") Long gameId,@Param("resourceType")String resourceType);
    
}
