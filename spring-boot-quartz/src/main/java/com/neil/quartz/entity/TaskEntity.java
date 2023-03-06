package com.neil.quartz.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Decription 定时任务实体类
 * @Author NEIL
 * @Date 2022/12/1 20:49
 * @Version 1.0
 */
public class TaskEntity implements Serializable {
    private static final long serialVersionUID = -2389020702315876172L;

    private long jobId;
    /**
     * 分组概念，每次启动若干组
     */
    private Integer jobGroupId;
    private String jobName;
    /**
     * 对应的Bean的类名
     */
    private String beanName;
    /**
     * 入参
     */
    private String params;
    /**
     * 时间表达式
     */
    private String cronExpr;
    private Integer beforeJobId;
    /**
     * 任务状态，0代表活跃，1代表死亡
     */
    private Integer status;

    private Map<String, Object> content;

    public TaskEntity() {
        content = new HashMap<>();
    }

    public TaskEntity(Map<String, Object> map) {
        if (map.get("JOB_ID") != null) {
            jobId = Integer.parseInt(map.get("JOB_ID").toString());
        }
        if (map.get("JOB_GROUP_ID") != null) {
            jobGroupId = Integer.parseInt(map.get("JOB_GROUP_ID").toString());
        }
        if (map.get("JOB_NAME") != null) {
            jobName = map.get("JOB_NAME").toString();
        }
        if (map.get("BEAN_NAME") != null) {
            beanName = map.get("BEAN_NAME").toString();
        }
        if (map.get("PARAMS") != null) {
            params = map.get("PARAMS").toString();
        }
        if (map.get("CRON_EXPR") != null) {
            cronExpr = map.get("CRON_EXPR").toString();
        }
        if (map.get("BEFORE_JOB_ID") != null) {
            beforeJobId = Integer.parseInt(map.get("BEFORE_JOB_ID").toString());
        }
        if (map.get("STATUS") != null) {
            status = Integer.parseInt(map.get("STATUS").toString());
        }
        content = new HashMap<>();
    }

    public void setContent(String key, Object value) {
        content.put(key, value);
    }

    public Object getContent(String key) {
        return content.get(key);
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(int jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public Integer getBeforeJobId() {
        return beforeJobId;
    }

    public void setBeforeJobId(Integer beforeJobId) {
        this.beforeJobId = beforeJobId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
