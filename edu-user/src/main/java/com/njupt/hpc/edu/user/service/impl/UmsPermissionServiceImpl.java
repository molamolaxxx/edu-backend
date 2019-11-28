package com.njupt.hpc.edu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.user.dao.UmsPermissionMapper;
import com.njupt.hpc.edu.user.dao.UmsRoleMapper;
import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户权限 服务实现类
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Service
public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission> implements UmsPermissionService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsPermissionMapper permissionMapper;

    /**
     * 根据用户id查询该用户的所有权限
     * @param userId
     * @return
     */
    @Override
    public List<UmsPermission> listPermissionListByUserId(String userId) {
        // 根据用户id获取roleid
        List<String> roleIdList = roleMapper.findRoleByUserId(userId).stream()
                .map(e -> e.getId()).collect(Collectors.toList());

        return permissionMapper.findPermissionByRoleIdList(roleIdList);
    }
}
