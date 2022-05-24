package geon.hee.tobyspring.repository;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    private MyConnectionMaker connectionMaker() {
        return new MyConnectionMaker();
    }
}
