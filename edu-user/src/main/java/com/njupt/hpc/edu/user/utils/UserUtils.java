package com.njupt.hpc.edu.user.utils;

import com.njupt.hpc.edu.common.exception.EduUserException;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-29 17:33
 **/
public class UserUtils {

    public static UmsUser getUserFromRequest(HttpServletRequest request, UmsUserService service){
        String username = (String) request.getAttribute("username");
        if (null == username){
            throw new EduUserException("用户名不存在于request中");
        }
        UmsUser user = service.findUserByUsername(username);
        if (null == user){
            throw new EduUserException("用户不存在");
        }
        return user;
    }
}
