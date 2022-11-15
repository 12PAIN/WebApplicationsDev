package rest.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class DataBase implements IDataBase{

    private String url = "jdbc:mysql://localhost:3306/WFSapp?serverTimezone=Europe/Moscow&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private String login = "root";
    private String password = "starwars123G";
    private String Driver = "com.mysql.cj.jdbc.Driver";
    private int initConnectionCount = 10; 

    private FConnectionPool factoryConnectionPool;
    private IConnectionPool connectionPool;
    
    public DataBase(){
        try{
            factoryConnectionPool = new FConnectionPool();
            connectionPool = factoryConnectionPool.getConnectionPool(url, login, password, Driver, initConnectionCount);
        }catch(Exception ex){ex.printStackTrace();};     
    }

    private Connection getConnection(){
        try{
            return connectionPool.retrieveConnection();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        catch(NullPointerException ex){
            return null;
        }
       
    }

    @Override
    public ArrayList<String> isUserCorrect(String login, String password){
        
        Connection conn = getConnection();

        try{
            

            if(conn != null){
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM users where login = (?)");
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();

                ArrayList<String> userData = new ArrayList<>();

                if(resultSet.isBeforeFirst()){
                    resultSet.next();
                    if(resultSet.getString("password").equals(password)){
                        userData.add(0, "true");
                        userData.add(1, resultSet.getString("email"));
                    }
                    else userData.add(0, "false");
                } else userData.add(0, "false");
                

                statement.close();
                resultSet.close();
                connectionPool.putback(conn);

                return userData;
            }else{

                connectionPool.putback(conn);
                return null;
            }

            

        }catch(SQLException ex){
            connectionPool.putback(conn);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean createUser(String login, String password, String email){    
        
        Connection conn = getConnection();

        try{

            if(conn != null){

                String sqlInsert = "INSERT INTO users(login, password, email) Values (?, ?, ?)";

                PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);

                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);

                int rows = preparedStatement.executeUpdate();

                preparedStatement.close();
                connectionPool.putback(conn);
                if(rows != 0){ return true;}
                else return false;

            }else{ 
                connectionPool.putback(conn);
                return null;
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            connectionPool.putback(conn);
            if(Integer.parseInt(ex.getSQLState()) == 23505 || Integer.parseInt(ex.getSQLState()) == 23000) return false;

            return null;
        }
        catch(NullPointerException ex){
            connectionPool.putback(conn);
            return null;
        }
    }

}
