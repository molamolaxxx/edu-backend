package com.njupt.hpc.edu.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.project.model.PmsData;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 算法数据表 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsDataService extends IService<PmsData> {

    /**
     * 根据实例id查找数据信息
     * @param instanceId
     * @return
     */
    PmsData findDataByInstanceId(String instanceId);

    /**
     * 新建数据
     * @param data
     */
    Boolean create(PmsData data);

    /**
     * 上传数据
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 更新数据
     * @param
     * @return
     */
    Boolean update(PmsData data);

    /**
     * 删除数据
     * @param dataId
     * @param userId
     * @return
     */
    Boolean remove(String dataId, String userId);
}
