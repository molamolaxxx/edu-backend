package com.njupt.hpc.edu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduUserException;
import com.njupt.hpc.edu.common.utils.JwtTokenUtil;
import com.njupt.hpc.edu.user.component.EduUserDetailsService;
import com.njupt.hpc.edu.user.dao.UmsUserMapper;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {

    @Autowired
    private UmsUserMapper userMapper;

    @Autowired
    private EduUserDetailsService detailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    @Override
    public UmsUser findUserByUsername(String username) {
        Map<String, Object> conditionMap = new HashMap();
        conditionMap.put("name", username);
        List<UmsUser> result = userMapper.selectByMap(conditionMap);
        if (result.size() == 0)
            return null;
        if (result.size() > 1)
            throw new EduUserException("相同用户名存在多个用户，请管理员检查");
        return result.get(0);
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        // 根据用户名获取user
        UserDetails userDetails = detailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null,
                        userDetails.getAuthorities());
        // 存入security的session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        return jwtTokenUtil.generateToken(userDetails);
    }


    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }
}
