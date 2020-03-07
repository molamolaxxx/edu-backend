package com.njupt.hpc.edu.project.data.content.graph;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 图谱内容vo，前端展现知识图谱内容
 * @date : 2020-02-26 12:47
 **/
@Data
public class GraphContentVO {

    /**
     * 实体map
     * {
     *      "1": {"name": "数据结构","type": "学科"},
     *      "2": {"name": "二叉树", "type": "知识点"},
     *      "3": {"name": "链表","type": "知识点"},
     *      "4": {"name": "平衡二叉树","type": "知识点"},
     *      "5": {"name": "二叉树的结构讲解","url": "www.mooc.com/15.html","type": "视频资源"},
     * }
     */
    private Map<String, Entity> entityMap;


    /**
     * 关系list
     * [
     *      { "source": 1, "target": 2, "rela": "包含", "type": "包含关系" },
     *      { "source": 1, "target": 3, "rela": "包含", "type": "包含关系" },
     *      { "source": 1, "target": 4, "rela": "包含", "type": "包含关系" },
     *      { "source": 2, "target": 5, "rela": "视频课程", "type": "资源" },
     *      { "source": 3, "target": 6, "rela": "ppt教程", "type": "资源" },
     * ]
     */
    private List<Relation> relationList;

    public GraphContentVO(Map<String, Entity> entityMap, List<Relation> relationList) {
        this.entityMap = entityMap;
        this.relationList = relationList;
    }
}
