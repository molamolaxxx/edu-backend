package com.njupt.hpc.edu.project.service.impl;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.sys.DataConfig;
import com.njupt.hpc.edu.project.algorithm.AlgorithmEntity;
import com.njupt.hpc.edu.project.algorithm.AlgorithmEntityRegistry;
import com.njupt.hpc.edu.project.manager.FileTransferManager;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.service.AlgorithmService;
import com.njupt.hpc.edu.project.service.PmsDataService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-11-12 14:12
 **/
@Service
public class AlgorithmServiceImpl implements AlgorithmService {

    @Resource
    private DataConfig dataConfig;

    @Resource
    private PmsDataService dataService;

    @Resource
    private AlgorithmEntityRegistry algorithmEntityRegistry;

    @Resource
    private FileTransferManager fileTransferManager;

    @Override
    public String heartBeat(String id) {
        algorithmEntityRegistry.heartBeat(id);
        return "ok";
    }

    @Override
    public String resultUpload(MultipartFile file) {
        return dataService.upload(file);
    }

    @Override
    public Boolean inputDataDownload(String dataId, HttpServletResponse response) {
        PmsData data = dataService.getById(dataId);
        if (null != data) {
            return fileTransferManager.transfer(data.getDataPath(), response);
        }
        return false;
    }

    @Override
    public Boolean contains(String algorithmId) {
        Map<String, AlgorithmEntity> algorithmMap = algorithmEntityRegistry.getAlgorithmMap();
        return algorithmMap.keySet().contains(algorithmId);
    }

    @Override
    public Boolean contains(Set<String> instanceIds) {
        return null;
    }

    @Override
    public List<AlgorithmEntity> getAlgorithmList() {
        return algorithmEntityRegistry.getAlgorithmMap().entrySet()
                .stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public AlgorithmEntity register(AlgorithmEntity algorithmEntity) {
        if (StringUtils.isEmpty(algorithmEntity.getName())) {
            throw new EduProjectException("算法名不能为空");
        }
        return algorithmEntityRegistry.register(algorithmEntity);
    }

    @Override
    public boolean offline(String id) {
        return algorithmEntityRegistry.offline(id);
    }

    @Override
    public Map<String, Set<AlgorithmEntity>> getInstanceTypeWithAlgorithmSet() {
        return algorithmEntityRegistry.getInstanceCodeMap();
    }

    @Override
    public boolean isInstanceContainerOnline(String instanceCode) {
        Map<String, Set<AlgorithmEntity>> instanceCodeMap = algorithmEntityRegistry.getInstanceCodeMap();
        if (!instanceCodeMap.keySet().contains(instanceCode)) {
            return false;
        }
        return instanceCodeMap.get(instanceCode).size() != 0;
    }
}
