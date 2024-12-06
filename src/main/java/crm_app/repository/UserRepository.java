package crm_app.repository;

import crm_app.config.MysqlConfig;
import crm_app.entity.UserEntity;
import crm_app.entity.UserTaskStatusDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<UserEntity> findByEmailAndPassword(String email, String password){

        List<UserEntity> listUserEntities = new ArrayList<UserEntity>();

        String query = "SELECT * FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ? AND u.password = ?";

        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(result.getInt("id"));
                entity.setEmail(result.getString("email"));
                entity.setName(result.getString("name"));
                listUserEntities.add(entity);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("findByEmailAndPassword : " + e.getMessage());
        }

        return listUserEntities;
    }

    public List<UserEntity> findAll(){

        List<UserEntity> list = new ArrayList<UserEntity>();

        String query = "SELECT u.id, u.fullname, u.email, r.name FROM users u JOIN roles r ON u.role_id = r.id";

        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(result.getInt("id"));
                entity.setEmail(result.getString("email"));
                entity.setName(result.getString("name"));
                entity.setFullname(result.getString("fullname"));
                list.add(entity);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("findAll : " + e.getMessage());
        }
        return list;

    }

    public int deleteById(int id) {
        String query = "DELETE FROM users u WHERE u.id = ?";

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

    public int insert(String email, String password, String fullname, int roleId) {
        int rowInsert = 0;
        String query = "INSERT INTO users(email,password,fullname,role_id)VALUES(?,?,?,?)";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setInt(4, roleId);

            rowInsert = statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("insert : " + e.getLocalizedMessage());
        }

        return rowInsert;
    }

    public UserEntity updateById(int id) {
        UserEntity user = null;
        String query = "SELECT * FROM users u WHERE u.id = ?";
        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,  id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                user = new UserEntity();
                user.setId(result.getInt("id"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setFullname(result.getString("fullname"));
                user.setRoleId(result.getInt("role_id"));
            }
        } catch (Exception e) {
            System.out.println("updateById" + e.getLocalizedMessage());
        }
        return user;
    }
    public void updateUser(int id, String email, String password, String fullname, int role_id) {
        String  query  = "UPDATE users u SET email = ?, password = ?, fullname = ?, role_id = ? WHERE id=?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setInt(4, role_id);
            statement.setInt(5, id);
            int rowUpdate = statement.executeUpdate();
            if (rowUpdate > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User update failed. No user found with id: " + id);
            }

        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getLocalizedMessage());
        }
    }
    public UserTaskStatusDTO findUserTaskAndStatus(int id){
        UserTaskStatusDTO dto = null;
        String query =  "SELECT u.fullname AS user_name, u.email AS user_email, "
                + "t.name AS task_name, s.name AS task_status "
                + "FROM users u "
                + "JOIN tasks t ON u.id = t.user_id "
                + "JOIN status s ON t.status_id = s.id "
                + "WHERE u.id = ?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                dto = new UserTaskStatusDTO();
                dto.setUserName(result.getString("user_name"));
                dto.setUserEmail(result.getString("user_email"));
                dto.setTaskName(result.getString("task_name"));
                dto.setTaskStatus(result.getString("task_status"));

            }
        } catch (Exception e) {
            System.out.println("findUserTaskAndStatus: " + e.getLocalizedMessage());
        }

        return dto;
    }

}
