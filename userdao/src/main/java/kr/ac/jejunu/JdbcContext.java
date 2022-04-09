package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User jdbcContextForFind(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
            preparedStatement = statementStrategy.makeStatement(connection);
//            sql 실행
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                User 에 데이터 매핑
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
//            자원 해지
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
//            sql 실행
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
//            User 에 데이터 매핑
            user.setId(resultSet.getInt(1));
        } finally {
//            자원 해지
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
        } finally {
//            자원 해지
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}