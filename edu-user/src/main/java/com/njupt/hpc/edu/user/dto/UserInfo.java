package com.njupt.hpc.edu.user.dto;

import com.njupt.hpc.edu.user.model.UmsPermission;
import com.njupt.hpc.edu.user.model.UmsRole;
import com.njupt.hpc.edu.user.model.UmsUser;
import lombok.Data;

import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-12-01 17:34
 **/
@Data
public class UserInfo {

    private UmsUser umsUser;

    private List<UmsRole> roles;

    private List<UmsPermission> permissions;
}
