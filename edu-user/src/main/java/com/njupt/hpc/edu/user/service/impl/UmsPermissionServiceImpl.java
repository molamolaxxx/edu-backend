package com.njupt.hpc.edu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduUserException;
import com.njupt.hpc.edu.user.dao.UmsPermissionMapper;
import com.njupt.hpc.edu.user.dao.UmsRoleMapper;
import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<UmsPermission> listPermissionsByUserId(String userId) {
        // 根据用户id获取roleid
        List<String> roleIdList = roleMapper.findRoleByUserId(userId).stream()
                .map(e -> e.getId()).collect(Collectors.toList());

        if (roleIdList.size() == 0){
            return new ArrayList<>();
        }
        return permissionMapper.findPermissionByRoleIdList(roleIdList);
    }

    /**
     * 删除角色权限关系
     * @param roleId
     * @param permissionId
     */
    @Override
    public void deleteRolePermissionRelation(String roleId, String permissionId) {
        Integer result = permissionMapper.deleteRolePermissionRelation(roleId, permissionId);
        if (result != 1){
            throw new EduUserException("删除角色权限关系失败");
        }
    }

    /**
     * 删除角色权限关系（批量）
     * @param roleId
     */
    @Override
    public void deleteRolePermissionRelationInBatch(String roleId, List<String> permissionIdList) {
        for (String pid : permissionIdList){
            this.deleteRolePermissionRelation(roleId, pid);
        }
    }

    /**
     * 添加角色权限关系
     * @param roleId
     * @param permissionId
     */
    @Override
    public void addRolePermissionRelation(String roleId, String permissionId) {
        // 判断角色权限是否都存在
        if (null == permissionMapper.selectById(permissionId)){
            throw new EduUserException("权限不存在");
        }
        if (null == roleMapper.selectById(roleId)){
            throw new EduUserException("角色不存在");
        }

        Integer result = permissionMapper.addRolePermissionRelation(roleId, permissionId);
        if (result != 1){
            throw new EduUserException("添加角色权限关系失败");
        }
    }

    @Override
    public void addRolePermissionRelationInBatch(String roleId, List<String> permissionIdList) {
        for (String pid : permissionIdList){
            this.addRolePermissionRelation(roleId, pid);
        }
    }

    @Override
    public List<UmsPermission> listPermissionsByRoleId(String roleId) {
        return permissionMapper.findPermissionByRoleId(roleId);
    }
}
