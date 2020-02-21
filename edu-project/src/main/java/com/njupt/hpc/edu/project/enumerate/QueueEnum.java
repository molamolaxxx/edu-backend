package com.njupt.hpc.edu.project.enumerate;

import lombok.Getter;

/**
 * @author : molamola
 * @Project: edu
 * @Description: 队列枚举信息
 * @date : 2020-01-30 11:14
 **/
@Getter
public enum QueueEnum {
    // java端给python端发送信息
    JAVA_TO_PYTHON_QUEUE("edu_j2p_mq","edu_j2p","edu_j2p_mq"),
    // 超时后的死信队列
    JAVA_TO_PYTHON_CANCEL("edu_j2p_mq_cancel","edu_j2p","edu_j2p_mq_cancel"),
    // python端给java端发送信息
    PYTHON_TO_JAVA_QUEUE("edu_p2j_mq",null,null);

    private String name;

    private String exchange;

    private String routeKey;

    public static final String JAVA_TO_PYTHON_QUEUE_NAME = "edu_j2p_mq";

    public static final String PYTHON_TO_JAVA_QUEUE_NAME = "edu_p2j_mq";

    public static final String PYTHON_TO_JAVA_QUEUE_CANCEL_NAME = "edu_j2p_mq_cancel";

    QueueEnum(String name, String exchange, String routeKey){
        this.name = name;
        this.exchange = exchange;
        this.routeKey = routeKey;
    }

}
