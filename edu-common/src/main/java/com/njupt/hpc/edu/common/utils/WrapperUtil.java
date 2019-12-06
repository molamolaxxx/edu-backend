package com.njupt.hpc.edu.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.util.Map;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-25 12:08
 **/
public class WrapperUtil {


    public static UpdateWrapper updateSingleWrapperBuilder(String column, Object value){
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set(column, value);
        return updateWrapper;
    }

    public static QueryWrapper querySingleWrapperBuilder(String column, Object value){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(column, value);
        return queryWrapper;
    }

    public static QueryWrapper queryWrapperBuilder(Map<String, Object> queryMap){
        QueryWrapper queryWrapper = new QueryWrapper();
        for (String key : queryMap.keySet()){
            queryWrapper.eq(key, queryMap.get(key));
        }
        return queryWrapper;
    }

    /**
     * 根据主键与用户id联合查询
     * @return
     */
    public static QueryWrapper queryByUserIdAndPK(String id, String uid){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        wrapper.eq("uid",uid);
        return wrapper;
    }

    /**
     * 根据主键与用户id联合查询
     * @return
     */
    public static UpdateWrapper updateByUserIdAndPK(String id, String uid){
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("id", id);
        wrapper.eq("uid",uid);
        return wrapper;
    }
}
