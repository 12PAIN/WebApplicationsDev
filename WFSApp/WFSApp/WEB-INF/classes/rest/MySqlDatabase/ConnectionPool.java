package rest.MySqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TreeSet;
import java.util.Random;

public class ConnectionPool implements IConnectionPool {
    
    private TreeSet<Connection> availableConns = new TreeSet<Connection>();
    private TreeSet<Connection> usedConns = new TreeSet<Connection>();
    private String url;
    private String login;
    private String password;


    private int maxConnectionsCount = 15;
    
    public ConnectionPool(String url, String login, String password, String driver, int initConnCnt) {
		try{
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.url = url;
		this.login = login;
		this.password = password;

        if(initConnCnt > maxConnectionsCount) initConnCnt = maxConnectionsCount;

		for (int i = 0; i < initConnCnt; i++) {
			availableConns.add(getConnection());
		}
	}

    private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, login, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

    private Connection checkConnReconnect(Connection conn) throws SQLException, NullPointerException{
        if(conn.isClosed() == true || conn == null || conn.isValid(0) == false){
            conn.close();
            conn = getConnection();
        }

        return conn;
    }

    @Override
    public synchronized Connection retrieveConnection() throws SQLException, NullPointerException {
		Connection newConn = null;

        if (availableConns.size() == 0) {

            //If used conn count is not maximum
            if(usedConns.size() < maxConnectionsCount){
                newConn = getConnection();
                usedConns.add(newConn);

                //If used conn count is maximum. Will return random of existing connections
            }else{

                Random rnd = new Random();
                
                int rndConnNum = rnd.nextInt(maxConnectionsCount);

                int i = 0;
                for(Connection conn : usedConns){
                    if(i == rndConnNum){
                        newConn = conn;
                        break;
                    }
                    i++;
                }

		    }
        }
        else{

            newConn = (Connection)availableConns.last();
            usedConns.add(newConn);
            availableConns.remove(newConn);
            
        }

        return checkConnReconnect(newConn);
	}

    @Override
    public synchronized void putback(Connection c) throws NullPointerException {
        if (c != null) {
            if (usedConns.remove(c)) {
                availableConns.add(c);
            } else {
                throw new NullPointerException("Connection not in the usedConns");
            }			
        }
    }

    @Override
    public int getAvailableConnsCnt() {
		return availableConns.size();
	}
}
