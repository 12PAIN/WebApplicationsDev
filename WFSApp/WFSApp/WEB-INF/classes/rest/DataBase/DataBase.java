package rest.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class DataBase implements IDataBase{

    private String url = "jdbc:mysql://localhost:3306/lab_1?serverTimezone=Europe/Moscow&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
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
    public Boolean isUserCorrect(String login, String password){
        
        try{
            Connection conn = getConnection();

            if(conn != null){
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()){
                    if(resultSet.getString("login").equals(login)){
                        if(resultSet.getString("password").equals(password)){
                            statement.close();
                            resultSet.close();
                            return true;
                        }
                    } else{continue;}
                }

                statement.close();
                resultSet.close();

                connectionPool.putback(conn);

                return false;
            }else{
                return null;
            }

            

        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean createUser(String login, String password, String email){    
        try{
            Connection conn = getConnection();

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

            }else return null;

        }catch(SQLException ex){
            ex.printStackTrace();

            if(ex.getSQLState() == "23505") return false;

            return null;
        }
        catch(NullPointerException ex){
            return null;
        }
    }

    @Override
    public Integer addRow(String name, Integer price, String description){    
        try{
            if(name != null && price != null && description != null){
                Connection conn = getConnection();
            
                if(conn != null){
                    String sqlInsert = "INSERT INTO products(ProductName, Price, Description) Values (?, ?, ?)";

                    PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);

                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, price);
                    preparedStatement.setString(3, description);     

                    int rows = preparedStatement.executeUpdate();
                    preparedStatement.close();

                    connectionPool.putback(conn);
                    return rows;
                }else return null;
            }else return 0;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        catch(NullPointerException ex){
            return null;
        }
    }

    @Override
    public Integer deleteRows(ArrayList<Integer> to_delete){
        try{
            
            if(to_delete != null){

                String sqlDelete = "DELETE FROM products WHERE id in (?)";

                int rows = 0;

                String toDeleteStr = "";

                Connection conn = getConnection();

                if(conn != null){
                    PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete);
                    boolean first = true;
                    for(Integer to_delete_row: to_delete){
                        if(first != true) toDeleteStr += ", " + String.valueOf(to_delete_row);
                        else{
                            first = false;
                            toDeleteStr += String.valueOf(to_delete_row);
                        }
                    }

                    preparedStatement.setString(1, toDeleteStr);

                    rows = preparedStatement.executeUpdate();

                    preparedStatement.close();

                }

                connectionPool.putback(conn);
                return rows;

            }else return 0;

        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        catch(NullPointerException ex){
            return null;
        }
    }

    @Override
    public ArrayList<ArrayList<String>> selectProducts(){
        try{
            Connection conn = getConnection();

            if(conn != null){

                PreparedStatement statement = conn.prepareStatement("SELECT * FROM products");
                ResultSet resultSet = statement.executeQuery();
                
                ArrayList<ArrayList<String>> strResultSet = new ArrayList<>();
                
                while(resultSet.next()){
                    ArrayList<String> row = new ArrayList<>();
                
                    row.add(String.valueOf(resultSet.getInt("id")));
                    row.add(resultSet.getString("ProductName"));
                    row.add(String.valueOf(resultSet.getInt("Price")));
                    row.add(resultSet.getString("Description"));
                
                    strResultSet.add(row);
                }
            
                resultSet.close();
                statement.close();
                
                connectionPool.putback(conn);

                if(strResultSet.size() > 0) return strResultSet;
                else return null;

            }else return null;

        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        catch(NullPointerException ex){
            return null;
        }
    }
}
