package com.njupt.hpc.edu.user.service;

import com.njupt.hpc.edu.project.enumerate.InstanceActionType;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-01-05 19:36
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PmsInstanceServiceImpl {

    @Autowired
    private PmsInstanceService service;

    @Test
    public void updateInstanceState(){
        service.updateInstanceState("instance_FQDP8U2C", InstanceActionType._ERROR);
    }
}
