package com.njupt.hpc.edu.user.model.dto;

import lombok.Data;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 用户dto
 * @date : 2020-02-14 14:59
 **/
@Data
public class UserInfoDTO {

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像路径
     */
    private String headerIcon;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 用户邮箱
     */
    private String email;
}
