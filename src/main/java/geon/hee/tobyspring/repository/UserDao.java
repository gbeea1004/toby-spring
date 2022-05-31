package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final JdbcContext jdbcContext;
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.jdbcContext = new JdbcContext(dataSource);
        this.dataSource = dataSource;
    }

    /**
     * 유저 회원가입
     *
     * @param user 유저 정보
     */
    public void add(User user) throws SQLException {
        jdbcContext.workWithStatementStrategy(con -> {
            PreparedStatement pstmt = con.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            return pstmt;
        });
    }

    /**
     * 유저 조회
     *
     * @param id 유저 아이디
     * @return 조회된 유저 정보
     */
    public User get(String id) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from users where id = ?");
        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        pstmt.close();
        con.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    /**
     * 유저 전체 삭제
     */
    public void deleteAll() throws SQLException {
        jdbcContext.workWithStatementStrategy(con -> con.prepareStatement("delete from users"));
    }

    public int getCount() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("select count(*) from users");

            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
