package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;

import java.sql.*;

public class UserDao {

    /**
     * 유저 회원가입
     *
     * @param user 유저 정보
     */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
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
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from users where id = ?");
        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        pstmt.close();
        con.close();

        return user;
    }

    private Connection getConnection() throws SQLException {
//        Class.forName("com.mysql.jdbc.Driver"); // Class.forName()은 JDBC 4.0 이후로는 메소드를 호출하지 않아도 자동으로 드라이버를 초기화한다.
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/springbook", "sa", "");
    }
}
