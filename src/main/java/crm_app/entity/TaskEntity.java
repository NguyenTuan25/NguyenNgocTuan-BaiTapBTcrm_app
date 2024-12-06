package crm_app.entity;

public class TaskEntity {
    private int id;
    private String taskName;
    private String jobName;
    private String jobStartDate;
    private String jobEndDate;
    private String userName;
    private String taskStatus;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobStartDate() {
        return jobStartDate;
    }
    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }
    public String getJobEndDate() {
        return jobEndDate;
    }
    public void setJobEndDate(String jobEndDate) {
        this.jobEndDate = jobEndDate;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
