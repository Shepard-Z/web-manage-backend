package com.pots.admin.mapper;

import com.pots.admin.model.dto.AdminGameI18nDTO;
import com.pots.admin.model.dto.AdminGameResDTO;
import com.pots.admin.model.vo.AdminGameDetailVO;
import com.pots.admin.model.vo.AdminGameSimpleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminGameMapper {
    
    List<AdminGameSimpleVO> findAllGameByLangCode(@Param("langCode") String langCode,
                                                  @Param("resourceType") String resourceType);
    
    AdminGameDetailVO findGameById(@Param("id") Long id);
    
    List<AdminGameI18nDTO> findAllGameI18nByGameId(@Param("gameId") Long gameId);
    
    List<AdminGameResDTO> findAllGameResImgByGameId(@Param("gameId") Long gameId,
                                                    @Param("resourceType") String resourceType);
    
}
