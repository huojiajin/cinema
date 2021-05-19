package howard.cinema.core.dao.entity.quartz;

import howard.cinema.core.dao.entity.common.StringUUIDEntity;

import java.time.LocalDateTime;

/**
 * @name: ScheduleTask
 * @description: 定时任务日志表
 * @author: huojiajin
 * @time: 2020/7/22 15:55
 */

public class ScheduleTaskLog extends StringUUIDEntity {

    private String taskName;//定时任务名称
    private LocalDateTime startTime;//开始执行时间
    private LocalDateTime endTime;//结束执行时间
    private Boolean system;//是否系统执行
    private Long execTime;//执行时长（秒）

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }
}
