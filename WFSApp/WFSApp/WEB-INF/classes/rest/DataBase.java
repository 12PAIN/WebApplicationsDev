package rest;

import java.sql.*;

import javax.swing.text.StyledEditorKit.BoldAction;

public class DataBase {
    private static DataBase instance;
    private static Connection conn = null;
    
    private DataBase() throws SQLException, SQLTimeoutException{
        try{Class.forName("com.mysql.cj.jdbc.Driver");} catch(Exception ex){}
        try{conn = getConnection();}catch (Exception ex){};
	}

    public static DataBase getInstance() throws SQLException, SQLTimeoutException, Exception{
        if(instance == null){
            instance = new DataBase();
        }
        if(conn == null || conn.isValid(1) == false || conn.isClosed() == true){
            conn = getConnection();
        }
        return instance;
    }

    private static Connection getConnection() throws SQLException, SQLTimeoutException{
        String url = "jdbc:mysql://localhost:3306/lab_1?serverTimezone=Europe/Moscow&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    	String username = "root";
    	String password = "starwars123G";

    	return DriverManager.getConnection(url, username, password);
    };

    public static boolean isUserCorrect(String login, String password) throws SQLException, SQLTimeoutException{
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
        return false;
    }

    public static Boolean createUser(String login, String password, String email) throws SQLException, SQLTimeoutException{     
        String sqlInsert = "INSERT INTO users(login, password, email) Values (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        int rows = preparedStatement.executeUpdate();
        preparedStatement.close();
        if(rows != 0){ return true;}
        else return false;
    }

    public static void addRow(String name, int price, String description) throws SQLException, SQLTimeoutException{     
        String sqlInsert = "INSERT INTO products(ProductName, Price, Description) Values (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, price);
        preparedStatement.setString(3, description);        
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void deleteRows(String[] to_delete) throws SQLException, SQLTimeoutException{
        String sqlDelete = "DELETE FROM products WHERE id = (?)";
        if(to_delete != null){
            PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete);
            for(String to_delete_row: to_delete){

                preparedStatement.setString(1, to_delete_row);

                preparedStatement.executeUpdate();

            }
            preparedStatement.close();
        }
    }

    public static int selectProductRowsCount() throws SQLException, SQLTimeoutException{
        PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) AS count_rows FROM products");
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int rows = resultSet.getInt("count_rows");
        statement.close();
		resultSet.close();
        return rows;
    }

    public static String[][] selectProducts() throws SQLException, SQLTimeoutException{

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM products");
        ResultSet resultSet = statement.executeQuery();

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
