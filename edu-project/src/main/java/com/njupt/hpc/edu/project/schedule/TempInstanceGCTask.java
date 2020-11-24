package com.njupt.hpc.edu.project.schedule;

import com.njupt.hpc.edu.common.sys.DataConfig;
import com.njupt.hpc.edu.project.enumerate.InstanceStateEnum;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.PmsInstance;
import com.njupt.hpc.edu.project.model.PmsResult;
import com.njupt.hpc.edu.project.service.PmsDataService;
import com.njupt.hpc.edu.project.service.PmsInstanceService;
import com.njupt.hpc.edu.project.service.PmsResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 负责对临时实例、数据结果进行gc
 * @date : 2020-11-24 10:57
 **/
@Configuration
@EnableScheduling
@Slf4j
public class TempInstanceGCTask {

    @Resource
    private PmsDataService dataService;

    @Resource
    private PmsInstanceService instanceService;

    @Resource
    private PmsResultService resultService;

    @Resource
    private DataConfig dataConfig;

    @Scheduled(fixedRate = 60000*1)
    public void tempInstanceGC() {
        // 1、获取所有temp实例
        List<PmsInstance> tempInstances = instanceService.getAllTempInstance();
        // 2、删除已完成的、创建时间一天以上的temp，以及对应的输入、结果数据
        Set<String> dataNeed = new HashSet<>();
        Set<String> pathNeed = new HashSet<>();
        for (PmsInstance instance : tempInstances) {
            if ((InstanceStateEnum.FINISH.getCode().equals(instance.getState()) ||
                    InstanceStateEnum.FAILED.getCode().equals(instance.getState())) &&
                    isOneDayAfterLater(instance.getCreateTime())) {
                dataService.remove(instance.getDataId());
                instanceService.removeById(instance.getId());
                continue;
            }
            dataNeed.add(instance.getDataId());
        }
        for (PmsResult result : resultService.list()) {
            if (result.getId().endsWith("temp") && isOneDayAfterLater(result.getCreateTime())) {
                resultService.delete(result.getId());
            } else {
                pathNeed.add(result.getPath());
            }
        }
        // 3、删除data-cache下所有不可达的文件
        for (PmsData data : dataService.list()) {
            if (!dataNeed.contains(data.getId()) && isOneDayAfterLater(data.getCreateTime())) {
                dataService.remove(data.getId());
            } else {
                pathNeed.add(data.getDataPath());
            }
        }
        File file = new File(dataConfig.getDataPath());
        if (file.exists() && file.isDirectory()) {
            for (File f : file.listFiles()){
                if (!pathNeed.contains(f.getPath())) {
                    f.delete();
                }
            }
        }
    }

    private boolean isOneDayAfterLater(LocalDateTime createTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getDayOfYear() - createTime.getDayOfYear() >= 1) {
            return true;
        }
        return false;
    }
}
