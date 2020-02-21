package com.njupt.hpc.edu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njupt.hpc.edu.common.exception.EduProjectException;
import com.njupt.hpc.edu.common.sys.DataConfig;
import com.njupt.hpc.edu.common.utils.BeanUtilsPlug;
import com.njupt.hpc.edu.common.utils.IdUtil;
import com.njupt.hpc.edu.common.utils.WrapperUtil;
import com.njupt.hpc.edu.project.dao.PmsDataMapper;
import com.njupt.hpc.edu.project.model.PmsData;
import com.njupt.hpc.edu.project.model.dto.DataDTO;
import com.njupt.hpc.edu.project.service.PmsDataService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 * 算法数据表 服务实现类
 * </p>
 *
 * @author molamola
 * @since 2019-12-04
 */
@Service
public class PmsDataServiceImpl extends ServiceImpl<PmsDataMapper, PmsData> implements PmsDataService {

    @Autowired
    private DataConfig dataConfig;

    private static HashMap<String, String> dataSuffixMap;

    private static String CACHE_SUFFIX = "cache";

    @PostConstruct
    public void setDataSuffixSet(){
        dataSuffixMap = new HashMap();
        dataSuffixMap.put("csv", "0");
        dataSuffixMap.put("json", "1");
        dataSuffixMap.put("rdf", "2");
    }

    @Override
    public Boolean create(DataDTO dto) {
        PmsData data = (PmsData) BeanUtilsPlug.copyPropertiesReturnTarget(dto, new PmsData());
        data.setId(IdUtil.generateId("data"));
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        // 1.检查数据路径（是否为空，是否存在数据, 是否为支持的数据类型）
        String dataPath = data.getDataPath();
        if (null == dataPath){
            throw new EduProjectException("数据路径为空");
        }
        File file = new File(dataPath);
        if (!file.exists()){
            throw new EduProjectException("数据路径不存在");
        }
        // 2.获取后缀
        Integer index = file.getName().lastIndexOf(".")+1;
        if (index != 0) {
            String suffix = file.getName().substring(index);
            // 3.判断后缀是否支持
            if (!dataSuffixMap.keySet().contains(suffix.toLowerCase())){
                throw new EduProjectException("不支持后缀"+suffix);
            }
            // 4.设置数据类型与数据大小
            data.setDataType(dataSuffixMap.get(suffix));
            data.setDataSize(new Long(file.length()).toString());
        }
        else {
            throw new EduProjectException("文件不存在后缀名");
        }
        // 5.保存数据
        this.save(data);
        return true;
    }

    @Override
    public String upload(MultipartFile file) {
        String rootPath = dataConfig.getDataPath();
        if (!rootPath.endsWith(File.separator))
            rootPath+=File.separator;
        checkSuffix(file.getOriginalFilename());
        String dataPath = rootPath+RandomStringUtils.randomAlphanumeric(8)+"-"+file.getOriginalFilename();

        // 上传
        try {
            FileUtils.writeByteArrayToFile(new File(dataPath), file.getBytes());
        } catch (IOException e) {
            throw new EduProjectException("上传文件失败");
        }

        return dataPath;
    }

    private void checkSuffix(String fileName){
        String suffix =fileName.substring(
                fileName.lastIndexOf(".")+1).toLowerCase();
        if (!dataSuffixMap.keySet().contains(suffix)){
            throw new EduProjectException("上传文件不支持后缀"+suffix);
        }
    }

    @Override
    public Boolean update(DataDTO dto, String dataId) {
        PmsData data = (PmsData) BeanUtilsPlug.copyPropertiesReturnTarget(dto, new PmsData());
        data.setUpdateTime(LocalDateTime.now());
        return this.update(data, WrapperUtil.queryByUserIdAndPK(dataId, data.getUid()));
    }

    @Override
    public Boolean remove(String dataId, String userId) {
        // 删除对应的文件
        PmsData data = this.getById(dataId);
        String dataPath = data.getDataPath();
        if (!FileUtils.deleteQuietly(new File(dataPath))){
            throw new EduProjectException("删除文件失败");
        }
        return this.remove(WrapperUtil.queryByUserIdAndPK(dataId, userId));
    }
}
