package com.njupt.hpc.edu.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 登录的表单
 * @date : 2019-11-28 15:01
 **/
@Data
public class LoginForm implements Serializable {

    private String username;

    private String password;
}
