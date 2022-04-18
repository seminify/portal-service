package kr.ac.jejunu;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Integer id) {
        String sql = "select * from userinfo where id = ?";
        Object[] params = new Object[]{id};
        return jdbcTemplate.query(
                sql,
                rs -> {
                    User user = null;
                    if (rs.next()) {
                        user = User.builder().id(rs.getInt("id")).name(rs.getString("name")).password(rs.getString("password")).build();
                    }
                    return user;
                },
                params);
    }

    public void insert(User user) {
        String sql = "insert into userinfo(name, password) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getPassword());
                    return preparedStatement;
                },
                keyHolder
        );
        user.setId(keyHolder.getKey().intValue());
    }

    public void update(User user) {
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        Object[] params = new Object[]{user.getName(), user.getPassword(), user.getId()};
        jdbcTemplate.update(sql, params);
    }

    public void delete(Integer id) {
        String sql = "delete from userinfo where id = ?";
        Object[] params = new Object[]{id};
        jdbcTemplate.update(sql, params);
    }
}