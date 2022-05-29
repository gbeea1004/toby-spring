package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class UserDao {

    private final DataSource dataSource;

    /**
     * 유저 회원가입
     *
     * @param user 유저 정보
     */
    public void add(User user) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        pstmt.executeUpdate();

        pstmt.close();
        con.close();
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
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        pstmt.close();
        con.close();

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement("delete from users");

        pstmt.executeUpdate();

        pstmt.close();
        con.close();
    }

    public int getCount() throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement("select count(*) from users");
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        pstmt.close();
        con.close();

        return count;
    }
}
