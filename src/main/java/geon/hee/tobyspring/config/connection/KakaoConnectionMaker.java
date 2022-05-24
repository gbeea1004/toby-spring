package geon.hee.tobyspring.config.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KakaoConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/kakao-db", "sa", "");
    }
}
