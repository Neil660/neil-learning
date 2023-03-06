package com.neil.quartz.task;

import com.mysql.jdbc.StringUtils;
import com.neil.aware.ServiceLocator;
import com.neil.quartz.util.TaskTrigger;
import com.neil.quartz.entity.TaskEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/1 20:51
 * @Version 1.0
 */
@Slf4j
public final class TaskManager {

    private JdbcTemplate jdbcTemplate = (JdbcTemplate) ServiceLocator.lookup("jdbcTemplate");

    private static TaskManager taskManager;

    private TaskTrigger taskTrigger;

    private Map<Integer, TaskEntity> taskCache = new ConcurrentHashMap<>();

    public TaskTrigger getTaskTrigger() {
        return taskTrigger;
    }

    public void setTaskTrigger(TaskTrigger taskTrigger) {
        this.taskTrigger = taskTrigger;
    }

    private TaskManager() {
        if (taskManager != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static TaskManager getInstance() {
        if (taskManager == null) {
            taskManager = new TaskManager();
        }
        return taskManager;
    }

    public void init() {
        try {
            String groupIds = System.getProperty("TASK_GROUP_ID");
            if (StringUtils.isNullOrEmpty(groupIds)) {
                groupIds = "1";
            }
            List<TaskEntity> taskNeedToRun = new ArrayList<>();
            List<Integer> groupId = new ArrayList<>();
            String[] split = groupIds.split(",");
            for (int i = 0; i < split.length; i++) {
                groupId.add(Integer.valueOf(split[i]));
            }

            // 从缓存取
            if (!taskCache.isEmpty()) {
                for (Entry<Integer, TaskEntity> entityEntry : taskCache.entrySet()) {
                    if (groupId.contains(entityEntry.getKey())) {
                        taskNeedToRun.add(entityEntry.getValue());
                    }
                }
            }
            else {
                String sql = "select job_id,job_group_id,job_name,bean_name,params,cron_expr,before_job_id,status " +
                        "from task_entity where status = 0";
                List<TaskEntity> taskEntities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TaskEntity.class));
                for (TaskEntity entity : taskEntities) {
                    int currGroupId = entity.getJobGroupId();
                    taskCache.put(currGroupId, entity);
                    if (groupId.contains(currGroupId)) {
                        taskNeedToRun.add(entity);
                    }
                }
            }

            for (TaskEntity entity : taskNeedToRun) {
                taskTrigger.cronTrigger(entity);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
