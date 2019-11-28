package com.njupt.hpc.edu.user.dao;

import com.njupt.hpc.edu.user.model.UmsPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户权限 Mapper 接口
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Repository
public interface UmsPermissionMapper extends BaseMapper<UmsPermission> {

    List<UmsPermission> findPermissionByRoleId(@Param("roleId") String roleId);

    List<UmsPermission> findPermissionByRoleIdList(List<String> ids);
}
