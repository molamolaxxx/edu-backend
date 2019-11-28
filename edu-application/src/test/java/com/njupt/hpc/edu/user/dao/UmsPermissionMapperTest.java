package com.njupt.hpc.edu.user.dao;

import com.njupt.hpc.edu.user.model.UmsPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-28 11:56
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UmsPermissionMapperTest {

    @Autowired
    private UmsPermissionMapper umsPermissionMapper;

    @Test
    public void findPermissionByRoleId(){
        List<UmsPermission> result = umsPermissionMapper.findPermissionByRoleId("2");
        System.out.println(result);
    }

    @Test
    public void findPermissionByRoleIdList(){
        List<UmsPermission> result = umsPermissionMapper.findPermissionByRoleIdList(Arrays.asList("1","2"));
        System.out.println(result);
    }
}