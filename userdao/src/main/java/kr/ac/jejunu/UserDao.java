package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
      StatementStrategy statementStrategy=new FindStatementStrategy();
      preparedStatement=statementStrategy.makeStatement(id,connection);
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
//        User 리턴
        return user;
    }

    public void insert(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
       StatementStrategy statementStrategy=new InsertStatementStrategy();
       preparedStatement=statementStrategy.makeStatement(user,connection);
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

    public void update(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
            StatementStrategy statementStrategy=new UpdateStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(user, connection);
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

    public void delete(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
//            드라이버 로딩
            connection = dataSource.getConnection();
//            sql 작성
            StatementStrategy statementStrategy = new DeleteStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(id, connection);
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