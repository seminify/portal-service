package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HallaUserDao extends UserDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        드라이버 로딩
        Class.forName("com.mysql.cj.jdbc.Driver");
//        커넥션
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/halla",
                "seminify",
                "Sm3979[]$"
        );
    }
}
