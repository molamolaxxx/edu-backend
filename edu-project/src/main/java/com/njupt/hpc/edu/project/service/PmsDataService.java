package com.njupt.hpc.edu.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.dto.DataDTO;
import com.njupt.hpc.edu.project.model.vo.DataVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 算法数据表 服务类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
public interface PmsDataService extends IService<PmsData> {

//    /**
//     * 新建数据
//     * @param dto
//     */
//    Boolean create(DataDTO dto);
    /**
     * 新建数据
     * @param dto
     */
    DataVO create(DataDTO dto);

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
    Boolean update(DataDTO dto, String userId);

    /**
     * 删除数据
     * @param dataId
     * @return
     */
    Boolean remove(String dataId);

    /**
     * 根据实例类型查找数据
     * @param typeId
     */
    List<DataVO> findByInstanceType(String typeId);

}
