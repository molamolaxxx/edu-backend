package com.njupt.hpc.edu.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author molamola
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * user_(八位随机)
     */
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 加密密码
     */
    private String password;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
