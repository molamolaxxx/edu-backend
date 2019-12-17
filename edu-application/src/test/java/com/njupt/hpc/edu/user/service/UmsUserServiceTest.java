package com.njupt.hpc.edu.user.service;

import com.njupt.hpc.edu.config.sys.DataConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-28 12:23
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UmsUserServiceTest {

    @Autowired
    private DataConfig config;

    @Test
    public void deleteUserRoleRelation(){
        System.out.println(config.toString());
    }

}
