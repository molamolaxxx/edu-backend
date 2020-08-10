package com.njupt.hpc.edu.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.project.dao.PmsResultMapper;
import com.njupt.hpc.edu.project.model.PmsResult;
import com.njupt.hpc.edu.project.model.dto.ResultDTO;
import com.njupt.hpc.edu.project.service.PmsResultService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    private PmsResultMapper pmsResultMapper;
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
        File file = new File(result.getPath());
        if (!file.exists()) {
            throw new EduProjectException("文件不存在");
        }
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(file.getName().getBytes("utf-8"), "utf-8"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        // 获得文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 装饰成bufferedStream
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        byte[] buffer = new byte[1024];
        int read = 0;
        while (-1 != (read = bufferedInputStream.read(buffer, 0, buffer.length))){
            bufferedOutputStream.write(buffer, 0, read);
        }
        if (null != bufferedInputStream) {
            bufferedInputStream.close();
        }
        if (null != bufferedOutputStream) {
            bufferedOutputStream.close();
        }
        return true;
    }

    @Override
    public List<PmsResult> findByUid(String uid) {
        return pmsResultMapper.findByUid(uid);
    }
}
