package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    public UserDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 유저 회원가입
     *
     * @param user 유저 정보
     */
    public void add(User user) {
        jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    /**
     * 유저 조회
     *
     * @param id 유저 아이디
     * @return 조회된 유저 정보
     */
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                userMapper,
                id);
    }

    /**
     * 유저 전체 삭제
     */
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    /**
     * 유저 수 조회
     */
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    /**
     * 유저 전체 조회
     */
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id",
                userMapper);
    }
}
