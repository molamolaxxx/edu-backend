package com.njupt.hpc.edu.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.dao.PmsResultMapper;
import com.njupt.hpc.edu.project.manager.FileTransferManager;
import com.njupt.hpc.edu.project.model.PmsResult;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.service.PmsResultService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Service
public class PmsResultServiceImpl extends ServiceImpl<PmsResultMapper, PmsResult> implements PmsResultService {

    @Resource
    private PmsResultMapper pmsResultMapper;

    @Resource
    private FileTransferManager fileTransferManager;

    @Override
    public Boolean create(ResultDTO dto) {
        if (dto.getId() == null) {
            dto.setId(IdUtil.generateId("result"));
        }
        PmsResult result = (PmsResult) BeanUtilsPlug.copyPropertiesReturnTarget(dto, new PmsResult());
        result.setCreateTime(LocalDateTime.now());
        result.setUpdateTime(LocalDateTime.now());
        return this.save(result);
    }

    @Override
    @Transactional
    public Boolean deleteByInstanceId(String instanceId) {
        QueryWrapper<PmsResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instance_id", instanceId);
        PmsResult result = this.getOne(queryWrapper);
        if (result != null) {
            // 删除对应的文件
            FileUtils.deleteQuietly(new File(result.getPath()));
            // 删除对应的结果记录
            return this.removeById(result.getId());
        }
        return false;
    }

    @Override
    public ResultDTO findByInstanceId(String instanceId) {
        QueryWrapper<PmsResult> wrapper = new QueryWrapper<>();
        wrapper.eq("instance_id", instanceId);
        PmsResult one = this.getOne(wrapper);
        return (ResultDTO) BeanUtilsPlug.copyPropertiesReturnTarget(one, new ResultDTO());
    }

    @Override
    public Boolean downloadDetail(String id, HttpServletResponse response) throws IOException {
        PmsResult result = this.getById(id);
        if (result == null) {
            throw new EduProjectException("结果明细记录不存在");
        }
        return fileTransferManager.transfer(result.getPath(), response);
    }

    @Override
    public List<PmsResult> findByUid(String uid) {
        return pmsResultMapper.findByUid(uid);
    }
}
