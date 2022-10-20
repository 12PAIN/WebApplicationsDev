package rest.MySqlDatabase;

public class FConnectionPool {
    public IConnectionPool getConnectionPool(String url, String login, String password, String driver, int initConnCnt){
        return new ConnectionPool(url, driver, login, password, initConnCnt);
    }
}
