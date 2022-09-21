import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Program{

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()){

                System.out.println("Connection to LAB_1 DB succesfull!");
                Statement statement = conn.createStatement();

                //СОЗДАНИЕ ТАБЛИЦЫ
                //String sqlCommand = "CREATE TABLE products (id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(30), Price INT, Description VARCHAR(30))";
                //statement.executeUpdate(sqlCommand);
                //System.out.println("Table \"Products\" are created!");



                //ВНЕСЕНИЕ ДАННЫХ В ТАБЛИЦУ
                //int rows = statement.executeUpdate("INSERT INTO products(ProductName,Price,Description) VALUES ('IPhone XR',55000,'New IPhone XR'),('Galaxy S21',80000,'New Galaxy S21')");
                //System.out.printf("Inserted %d rows", rows);


                //ОБНОВЛЕНИЕ ДАННЫХ В ТАБЛИЦЕ
                //int rows = statement.executeUpdate("UPDATE products SET Price = Price - 5000 WHERE ProductName = 'IPhone XR'");
                //System.out.printf("Updated %d rows", rows);


                //УДАЛЕНИЕ ДАННЫХ ИЗ ТАБЛИЦЫ
                //int rows = statement.executeUpdate("DELETE FROM products WHERE ProductName = 'Galaxy S21'");
                //System.out.printf("Deleted %d rows", rows);


                //ПОЛУЧЕНИЕ ДАННЫХ ИЗ ТАБЛИЦЫ
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count_rows FROM products");
		        resultSet.next();
		        int rows = resultSet.getInt("count_rows");
		        resultSet.close();

		        resultSet = statement.executeQuery("SELECT * FROM products");

		        int[] id = new int[rows];
		        String[] name = new String[rows];
		        int[] price = new int[rows];
		        String[] description = new String[rows];

                int i = 0;


                while(resultSet.next()){
                    id[i] = resultSet.getInt("id");
                    name[i] = resultSet.getString("ProductName");
                    price[i] = resultSet.getInt("Price");
                    description[i] = resultSet.getString("Description");
                    System.out.printf("ID %d. %s - %d - %s\n", id[i], name[i], price[i], description[i]);
                    i++;
                }
                System.out.printf("%d rows", rows);
		
        
                
                
                
                


                //ВВОД ДАННЫХ С КЛАВИАТУРЫ
                /*Scanner scanner = new Scanner(System.in);
                System.out.print("Input product name: ");
                String name = scanner.nextLine();
                System.out.print("Input product price: ");
                int price = scanner.nextInt();
                System.out.print("Input product Description: ");
                String description = scanner.nextLine();
                */

                //ВНЕСЕНИЕ ПЕРЕМЕННЫХ В ТАБЛИЦУ ЧЕРЕЗ ПРЕДПОДГОТОВЛЕННЫЙ ЗАПРОС
                /*
                String sqlInsert = "INSERT INTO products(ProductName, Price, Description) Values (?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, price);
                preparedStatement.setString(3, description);

                int rows = preparedStatement.executeUpdate();

                System.out.printf("%d rows added", rows);
                */
                

            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    public static Connection getConnection() throws SQLException, IOException{

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}