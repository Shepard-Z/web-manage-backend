package com.pots.admin.mapper;

import com.pots.admin.model.dto.AdminUserCheckDTO;
import com.pots.admin.model.vo.AdminUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminUserMapper {
    
    @Select("""
            select
            id,name,gender,introduction,img_url,password_hash
            from administrator
            where name = #{username}
            and is_active = true
            """)
    AdminUserVO findAdminByUsername(@Param("username")String username);
    
    @Select("""
            select
            id,name
            from administrator
            where name = #{name}
            and id = #{id}
            and is_active = true
            """)
    AdminUserCheckDTO checkAdminByIdName(@Param("id") Long id, @Param("name") String name);
}
