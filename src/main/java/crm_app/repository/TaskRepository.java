package crm_app.repository;

import crm_app.config.MysqlConfig;
import crm_app.entity.StatusEntity;
import crm_app.entity.TaskEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    public List<TaskEntity> findUserJobStatus(){
        List<TaskEntity> taskDetailDTOs = new ArrayList<TaskEntity>();
        String query = "SELECT tasks.id AS task_id,"
                + " tasks.name AS task_name, "
                + "jobs.name AS job_name, "
                + "DATE_FORMAT(jobs.start_date, '%d/%m/%Y') AS job_start_date, "
                + "DATE_FORMAT(jobs.end_date, '%d/%m/%Y') AS job_end_date, "
                + "users.fullname AS user_name, "
                + "status.name AS task_status "
                + "FROM tasks "
                + "JOIN jobs ON jobs.id = tasks.job_id "
                + "JOIN users ON tasks.user_id = users.id "
                + "JOIN status ON tasks.status_id = status.id;";

        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setId(result.getInt("task_id"));
                taskEntity.setTaskName(result.getString("task_name"));
                taskEntity.setJobName(result.getString("job_name"));
                taskEntity.setJobStartDate(result.getString("job_start_date"));
                taskEntity.setJobEndDate(result.getString("job_end_date"));
                taskEntity.setUserName(result.getString("user_name"));
                taskEntity.setTaskStatus(result.getString("task_status"));
                taskDetailDTOs.add(taskEntity);
            }
        } catch (Exception e) {
            System.out.println("findUserJobStatus : " + e.getLocalizedMessage());
        }
        return taskDetailDTOs;


    }

    public int deleteById(int id) {
        String query = "DELETE FROM tasks t WHERE t.id = ?";

        int rowDeleted = 0 ;
        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteById : " + e.getLocalizedMessage());
        }

        return rowDeleted;
    }

    public List<StatusEntity> findAll(){
        List<StatusEntity> list = new ArrayList<StatusEntity>();

        String query = "SELECT s.id, s.name FROM status s";

        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                StatusEntity entity = new StatusEntity();
                entity.setId(result.getInt("id"));
                entity.setName(result.getString("name"));
                list.add(entity);

            }
        } catch (Exception e) {
            System.out.println("findAll : " + e.getMessage());
        }
        return list;
    }
    public int insert(String taskName, int job_id, String jobStartDate, String jobEndDate, int user_id, int status_id) {
        int rowInsert = 0;
        String query = "INSERT INTO tasks(name,job_id,start_date,end_date,status_id,user_id)VALUES(?,?,?,?,?,?)";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskName);
            statement.setInt(2, job_id);
            statement.setString(3, jobStartDate);
            statement.setString(4, jobEndDate);
            statement.setInt(5, user_id);
            statement.setInt(6, status_id);

            rowInsert = statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("insert : " + e.getLocalizedMessage());
        }

        return rowInsert;
    }

    // Phương thức lấy task theo ID
    public TaskEntity findById(int id) {
        TaskEntity taskEntity = null;
        String query = "SELECT t.id AS task_id, t.name AS task_name, "
                + "j.name AS job_name, j.start_date AS job_start_date, j.end_date AS job_end_date, "
                + "u.fullname AS user_name, s.name AS task_status "
                + "FROM tasks t "
                + "JOIN jobs j ON j.id = t.job_id "
                + "JOIN users u ON t.user_id = u.id "
                + "JOIN status s ON t.status_id = s.id "
                + "WHERE t.id = ?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                taskEntity = new TaskEntity();
                taskEntity.setId(result.getInt("task_id"));
                taskEntity.setTaskName(result.getString("task_name"));
                taskEntity.setJobName(result.getString("job_name"));
                taskEntity.setJobStartDate(result.getString("job_start_date"));
                taskEntity.setJobEndDate(result.getString("job_end_date"));
                taskEntity.setUserName(result.getString("user_name"));
                taskEntity.setTaskStatus(result.getString("task_status"));
            }
        } catch (Exception e) {
            System.out.println("findById: " + e.getLocalizedMessage());
        }
        return taskEntity;
    }

    // Phương thức cập nhật task
    public int updateTask(int id, String taskName, int job_id, String jobStartDate, String jobEndDate, int user_id, int status_id) {
        String query = "UPDATE tasks t SET t.name = ?, t.job_id = ?, t.start_date = ?, t.end_date = ?, t.user_id = ?, t.status_id = ? WHERE t.id = ?";
        Connection connection = MysqlConfig.getConnection();
        int rowsUpdated = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskName);
            statement.setInt(2, job_id);
            statement.setDate(3, java.sql.Date.valueOf(jobStartDate));
            statement.setDate(4, java.sql.Date.valueOf(jobEndDate));
            statement.setInt(5, user_id);
            statement.setInt(6, status_id);
            statement.setInt(7, id);
            rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User update failed. No user found with id: " + id);
            }
        } catch (Exception e) {
            System.out.println("updateTask: " + e.getLocalizedMessage());
        }
        return rowsUpdated;
    }
}
