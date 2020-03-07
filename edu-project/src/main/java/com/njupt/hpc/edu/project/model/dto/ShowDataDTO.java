package com.njupt.hpc.edu.project.model.dto;

import lombok.Data;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 算法展示部分dataDTO
 * @date : 2020-03-07 18:50
 **/
@Data
public class ShowDataDTO extends DataDTO{

    /**
     * 比datadto多一个id，用于开始时的查找定位
     */
    private String id;
}
