package geon.hee.tobyspring.repository;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CountingConnectionMaker implements ConnectionMaker {

    private final ConnectionMaker realConnectionMaker;
    private int counter = 0;

    @Override
    public Connection makeConnection() throws SQLException {
        counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
