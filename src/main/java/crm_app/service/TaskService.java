package crm_app.service;

import crm_app.entity.JobEntity;
import crm_app.entity.StatusEntity;
import crm_app.entity.TaskEntity;
import crm_app.entity.UserEntity;
import crm_app.repository.JobRepository;
import crm_app.repository.TaskRepository;
import crm_app.repository.UserRepository;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepository = new TaskRepository();

    private TaskRepository deleteTaskRepository = new TaskRepository();

    private TaskRepository addTaskRepository = new TaskRepository();

    private JobRepository jobRepository = new JobRepository();

    private UserRepository userRepository = new UserRepository();

    public List<TaskEntity> getAllTask(){
        return taskRepository.findUserJobStatus();
    }

    public boolean deleteUserById(int id) {

        int count = deleteTaskRepository.deleteById(id);

        return count > 0 ;
    }
    public List<StatusEntity> getAllStatus(){
        return taskRepository.findAll();
    }
    public List<JobEntity> getAllJob(){
        return jobRepository.findAll();
    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }
    public boolean insertTask(String taskName, int job_id, String jobStartDate, String jobEndDate,int status_id, int user_id) {
        return addTaskRepository.insert(taskName, job_id, jobStartDate, jobEndDate,status_id, user_id) > 0;
    }

    public TaskEntity getTaskById(int id){
        return taskRepository.findById(id);
    }

    public boolean updateTask(int id, String taskName, int job_id, String jobStartDate, String jobEndDate, int user_id, int status_id) {
        return taskRepository.updateTask(id, taskName, job_id, jobStartDate, jobEndDate, user_id, status_id) > 0;
    }

}
