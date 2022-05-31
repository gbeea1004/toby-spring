package geon.hee.tobyspring.repository;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcContext {

    private final DataSource dataSource;

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();

            pstmt = stmt.makePreparedStatement(con);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {}
            }
        }
    }
}
