package com.njupt.hpc.edu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.exception.EduUserException;
import com.njupt.hpc.edu.common.sys.UserConfig;
import com.njupt.hpc.edu.common.utils.JwtTokenUtil;
import com.njupt.hpc.edu.user.component.EduUserDetailsService;
import com.njupt.hpc.edu.user.dao.UmsUserMapper;
import com.njupt.hpc.edu.user.model.UmsUser;
import com.njupt.hpc.edu.user.service.UmsUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
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

    @Autowired
    private UserConfig userConfig;

    private HashSet<String> allowHeaderSuffixSet = new HashSet<>();

    @PostConstruct
    public void initAllowHeaderSuffixSet(){
        allowHeaderSuffixSet.add("jpg");
        allowHeaderSuffixSet.add("jpeg");
        allowHeaderSuffixSet.add("png");
        allowHeaderSuffixSet.add("bmp");
    }

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

    /**
     * 用户头像上传
     * @param header
     * @return
     */
    @Override
    public String uploadHeaderIcon(MultipartFile header, String userId) {
        String iconRootPath = userConfig.getHeaderIconPath();
        if (!iconRootPath.endsWith(File.separator))
            iconRootPath += File.separator;
        checkSuffix(header.getOriginalFilename());
        // 以用户id构建iconPath
        String iconPath = iconRootPath + userId + "-" + header.getOriginalFilename();

        try {
            FileUtils.writeByteArrayToFile(new File(iconPath), header.getBytes());
        } catch (IOException e) {
            throw new EduProjectException("上传头像失败");
        }
        // 拼接icon的url
        String iconUrl = "/iconHeader/"+userId + "-" + header.getOriginalFilename();

        return iconUrl;
    }

    @Override
    public void updatePassword(String old, String now, UmsUser user) {
        // 1.判断老密码是否正确
        // 根据用户名获取user
        UserDetails userDetails = detailsService.loadUserByUsername(user.getName());
        if (!passwordEncoder.matches(old, userDetails.getPassword())){
            throw new BadCredentialsException("原密码不正确");
        }
        // 2.修改密码与更新时间
        user.setPassword(passwordEncoder.encode(now));
        user.setUpdateTime(LocalDateTime.now());

        // 3.更新
        this.updateById(user);
    }

    private void checkSuffix(String fileName){
        String suffix =fileName.substring(
                fileName.lastIndexOf(".")+1).toLowerCase();

        if (!allowHeaderSuffixSet.contains(suffix)){
            throw new EduProjectException("上传头像不支持后缀"+suffix);
        }
    }
}
