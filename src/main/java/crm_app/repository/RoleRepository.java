package crm_app.repository;

import crm_app.config.MysqlConfig;
import crm_app.entity.RoleEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    public List<RoleEntity> findAll(){
        List<RoleEntity> roleEntities = new ArrayList<RoleEntity>();
        String query = "SELECT * FROM roles";
        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                RoleEntity entity = new RoleEntity();
                entity.setId(result.getInt("id"));
                entity.setName(result.getString("name"));
                entity.setDescription(result.getString("description"));
                roleEntities.add(entity);
            }
        } catch (Exception e) {
            System.out.println("findAll : " + e.getLocalizedMessage());
        }

        return roleEntities;
    }

    public int deleteById (int id) {
        String query = "DELETE FROM roles r WHERE r.id = ?";
        int rowDeleted = 0 ;
        Connection connection = MysqlConfig.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteById" + e.getLocalizedMessage());
        }
        return rowDeleted;
    }

    public int insert(String name, String description) {
        int rowInsert = 0;
        String query = "INSERT INTO roles(name,description)VALUES(?,?)";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, description);

            rowInsert = statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("insert : " + e.getLocalizedMessage());
        }

        return rowInsert;
    }
    public RoleEntity updateRoleById(int id){
        RoleEntity role = null;
        String query = "SELECT * FROM roles r WHERE r.id = ?";
        Connection connection = MysqlConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                role = new RoleEntity();
                role.setId(result.getInt("id"));
                role.setName(result.getString("name"));
                role.setDescription(result.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("updateRoleById : " + e.getLocalizedMessage());
        }
        return role;
    }
    public int updateRole (int id, String name, String description) {
        String query = "UPDATE roles r SET r.name = ?, r.description = ? WHERE r.id = ?";
        Connection connection = MysqlConfig.getConnection();
        int rowUpdated = 0 ;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);
            rowUpdated = statement.executeUpdate();
            if (rowUpdated > 0) {
                System.out.println("Role updated successfully.");
            } else {
                System.out.println("Role update failed. No role found with id: " + id);
            }
        } catch (Exception e) {
            System.out.println("updateRoleById" + e.getLocalizedMessage());
        }

        return rowUpdated;
    }
}
