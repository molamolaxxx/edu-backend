package com.njupt.hpc.edu.project.service;

import com.njupt.hpc.edu.project.algorithm.AlgorithmEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-11-12 14:12
 **/
public interface AlgorithmService {

    /**
     * 心跳
     * @param id
     * @return "ok" or ""
     */
    String heartBeat(String id);

    /**
     * 结果上传
     * @param file
     * @return path
     */
    String resultUpload(MultipartFile file);

    /**
     * 注册算法
     * @return
     */
    AlgorithmEntity register(AlgorithmEntity algorithmEntity);

    /**
     * 算法服务下架
     */
    boolean offline(String id);

    /**
     * 算法输入下载
     * @param dataId
     * @return
     */
    Boolean inputDataDownload(String dataId, HttpServletResponse response);

    /**
     * 是否包含某个算法
     */
    Boolean contains(String algorithmId);

    /**
     * 是否包含某个算法
     */
    Boolean contains(Set<String> instanceIds);

    /**
     * 获取算法列表
     * @return
     */
    List<AlgorithmEntity> getAlgorithmList();

    /**
     * 获取实例id：Set<算法>
     * @return
     */
    Map<String, Set<AlgorithmEntity>> getInstanceTypeWithAlgorithmSet();

    /**
     * 实例容器（算法）是否在线？
     * @return
     */
    boolean isInstanceContainerOnline(String instanceCode);
}
