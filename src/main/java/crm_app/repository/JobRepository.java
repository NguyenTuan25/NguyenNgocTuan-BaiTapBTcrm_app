package crm_app.repository;

import crm_app.config.MysqlConfig;
import crm_app.entity.JobEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    public List<JobEntity> findAll(){
        List<JobEntity> jobEntities = new ArrayList<JobEntity>();
        String query = "SELECT id, name, DATE_FORMAT(start_date, '%d/%m/%Y') AS start_date, DATE_FORMAT(end_date, '%d/%m/%Y') AS end_date\r\n"
                + "FROM jobs;\r\n"
                + "";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setId(result.getInt("id"));
                jobEntity.setName(result.getString("name"));
                jobEntity.setStart_date(result.getString("start_date"));
                jobEntity.setEnd_date(result.getString("end_date"));
                jobEntities.add(jobEntity);
            }
        } catch (Exception e) {
            System.out.println("findAll : " + e.getLocalizedMessage());
        }
        return jobEntities;
    }

    public int deleteById(int id) {
        String query = "DELETE FROM jobs j WHERE j.id = ?";

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
    public int insert(String name, String start_date, String end_date) {
        int rowInsert = 0;
        String query = "INSERT INTO jobs(name,start_date,end_date)VALUES(?,?,?)";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, start_date);
            statement.setString(3, end_date);

            rowInsert = statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("insert : " + e.getLocalizedMessage());
        }

        return rowInsert;
    }

    public JobEntity updateJobById(int id){
        JobEntity job = null;
        String query = "SELECT * FROM jobs j WHERE j.id = ?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                job = new JobEntity();
                job.setId(result.getInt("id"));
                job.setName(result.getString("name"));
                job.setStart_date(result.getString("start_date"));
                job.setEnd_date(result.getString("end_date"));
            }
        } catch (Exception e) {
            System.out.println("updateJobById : " + e.getLocalizedMessage());
        }
        return job;
    }
    public int updateJob (int id, String name, String start_date, String end_date) {
        String query = "UPDATE jobs j SET j.name = ?, j.start_date = ?, j.end_date = ? WHERE j.id = ?";
        Connection connection = MysqlConfig.getConnection();
        int rowUpdated = 0 ;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, start_date);
            statement.setString(3, end_date);
            statement.setInt(4, id);
            rowUpdated = statement.executeUpdate();
            if (rowUpdated > 0) {
                System.out.println("Job updated successfully.");
            } else {
                System.out.println("Job update failed. No role found with id: " + id);
            }
        } catch (Exception e) {
            System.out.println("updateJob" + e.getLocalizedMessage());
        }

        return rowUpdated;
    }
}
