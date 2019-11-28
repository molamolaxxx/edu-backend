package com.njupt.hpc.edu.user.dao;

import com.njupt.hpc.edu.user.model.UmsRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-27 21:57
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UmsRoleMapperTest {

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Test
    public void findRoleByUserId(){
        List<UmsRole> result = umsRoleMapper.findRoleByUserId("3212131wqe");
        System.out.println(result);
    }
}
