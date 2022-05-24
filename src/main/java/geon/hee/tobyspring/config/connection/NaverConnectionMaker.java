package geon.hee.tobyspring.config.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NaverConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/naver-db", "sa", "");
    }
}
