package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
public class AddStatement implements StatementStrategy {

    private User user;

    @Override
    public PreparedStatement makePreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        return pstmt;
    }
}
