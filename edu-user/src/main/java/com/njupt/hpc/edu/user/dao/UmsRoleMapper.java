package com.njupt.hpc.edu.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njupt.hpc.edu.user.model.UmsRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Repository
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据userId查询role
     */
    List<UmsRole> findRoleByUserId(@Param("userId") String userId);
}
