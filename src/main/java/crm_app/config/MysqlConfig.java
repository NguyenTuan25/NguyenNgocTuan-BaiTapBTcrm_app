package crm_app.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
    public  static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/crm_app", "root", "tuan0205");
        } catch (Exception e) {
            System.out.println("Lỗi kết nối csdl" + e.getMessage());
        }

        return connection;
    }
}
