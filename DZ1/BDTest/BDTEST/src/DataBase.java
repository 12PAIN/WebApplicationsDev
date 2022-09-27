package db;

import java.sql.*;

public class DataBase {
    private static DataBase instance;
    private Connection conn = null;
    private int count = 0;
    private DataBase(){
        try{Class.forName("com.mysql.cj.jdbc.Driver");} catch(Exception ex){}
        try{this.conn = getConnection();}catch (Exception ex){};
	}

    public static DataBase getInstance(){
        if(instance == null){
            instance = new DataBase();
        }
        return instance;
    }

    public int count(){
        count++;
        return this.count;
    }

    private Connection getConnection(){
        String url = "jdbc:mysql://localhost/LAB_1?serverTimezone=Europe/Moscow&useSSL=false";
    	String username = "root";
    	String password = "starwars123G";

    	try{return DriverManager.getConnection(url, username, password);}
        catch(SQLException ex){return (Connection)(null);}
    };

    public boolean isUserCorrect(String login, String password) throws SQLException{
        Statement statement = this.conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

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
        return false;
    }

    public void createUser(String login, String password) throws SQLException{
        Statement statement = this.conn.createStatement();       
        String sqlInsert = "INSERT INTO users(login, password) Values (?, ?)";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlInsert);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);        
        preparedStatement.executeUpdate();
        statement.close();
    }

    public void addRow(String name, int price, String description) throws SQLException{
        Statement statement = this.conn.createStatement();       
        String sqlInsert = "INSERT INTO products(ProductName, Price, Description) Values (?, ?, ?)";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sqlInsert);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, price);
        preparedStatement.setString(3, description);        
        preparedStatement.executeUpdate();
        statement.close();
    }

    public void deleteRows(String[] to_delete) throws SQLException{
        String sqlDelete = "DELETE FROM products WHERE id = (?)";
        if(to_delete != null){
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlDelete);
            for(String to_delete_row: to_delete){

                preparedStatement.setString(1, to_delete_row);

                preparedStatement.executeUpdate();

            }
            preparedStatement.close();
        }
    }

    public int selectProductRowsCount() throws SQLException{
        Statement statement = this.conn.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count_rows FROM products");
		resultSet.next();
		int rows = resultSet.getInt("count_rows");
        statement.close();
		resultSet.close();
        return rows;
    }

    public String[][] selectProducts() throws SQLException{

        Statement statement = this.conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

        int rows = selectProductRowsCount();

        String[][] strResultSet = new String[rows][4];

        int i = 0;
        while(resultSet.next()){
            strResultSet[i][0] = String.valueOf(resultSet.getInt("id"));
            strResultSet[i][1] = resultSet.getString("ProductName");
            strResultSet[i][2] = String.valueOf(resultSet.getInt("Price"));
            strResultSet[i][3] = resultSet.getString("Description");  
            i++;
        }
        resultSet.close();
        statement.close();
        return strResultSet;
    }
}
