package com.njupt.hpc.edu.common.utils;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2019-11-25 12:08
 **/
public class WrapperUtil {

    public static UpdateWrapper updateWrapperBuilder(R column, Object value){
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set(column, value);
        return updateWrapper;
    }
}
