package com.njupt.hpc.edu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.user.model.UmsUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Transactional
public interface UmsUserService extends IService<UmsUser> {

    UmsUser findUserByUsername(String username);

    String login(String username, String password);

    String refreshToken(String token);

    String uploadHeaderIcon(MultipartFile header, String userId);

    void updatePassword(String old, String now, UmsUser user);
}
