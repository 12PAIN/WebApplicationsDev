


import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

import db.DataBase;

public class Program{

    public static void main(String[] args) {
        try{
            
            DataBase db = DataBase.getInstance();


            DataBase db1 = DataBase.getInstance();
            DataBase db2 = DataBase.getInstance();
            DataBase db3 = DataBase.getInstance();
            int c1 = db.count();
            int c2 = db1.count();
            int c3 = db2.count();
            int c4 = db3.count();
            int c5 = db.count();

            System.out.printf("%d gets\n", c1);
            System.out.printf("%d gets\n", c2);
            System.out.printf("%d gets\n", c3);
            System.out.printf("%d gets\n", c4);
            System.out.printf("%d gets\n", c5);
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}
