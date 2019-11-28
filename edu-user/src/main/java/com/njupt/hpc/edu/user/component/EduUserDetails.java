package com.njupt.hpc.edu.user.component;

import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.model.UmsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 项目的userservice
 * @date : 2019-11-27 15:19
 **/
public class EduUserDetails implements UserDetails {

    //用户信息
    private UmsUser user;
    //用户权限
    private List<UmsPermission> permissionList;

    public EduUserDetails(UmsUser user, List<UmsPermission> permissionList) {
        this.user = user;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将permission 转化成 SimpleGrantedAuthority对象传入UserDetail交给ss保存
        return permissionList.stream()
                .filter(permission -> permission.getValue()!= null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
