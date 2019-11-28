package com.njupt.hpc.edu.user.component;

import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsPermissionService;
import com.njupt.hpc.edu.user.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-27 15:40
 **/
@Component
public class EduUserDetailsService implements UserDetailsService {

    @Autowired
    private UmsPermissionService permissionService;

    @Autowired
    private UmsUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查找用户
        UmsUser user = userService.findUserByUsername(username);
        if (null == user){
            //抛出usernamenotfound异常
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 构建userDetail
        return new EduUserDetails(user, permissionService.listPermissionListByUserId(user.getId()));
    }
}
