package rest.DataBase;

public class FConnectionPool {
    public IConnectionPool getConnectionPool(String url, String login, String password, String driver, int initConnCnt){
        return ConnectionPool.initConnectionPool(url, driver, login, password, initConnCnt);
    }
}
