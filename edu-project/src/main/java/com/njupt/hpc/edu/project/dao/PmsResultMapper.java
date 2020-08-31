package com.njupt.hpc.edu.project.dao;

import com.njupt.hpc.edu.project.model.PmsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Repository
public interface PmsResultMapper extends BaseMapper<PmsResult> {
    /**
    *@Description: 根据uid查询所有评价结果
    *@Param:
    *@return:
    *@Author: Su
    */
    List<PmsResult> findByUid(String uid);

}
