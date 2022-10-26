package rest.Model;

import java.util.ArrayList;

import rest.DataBase.FDataBase;
import rest.DataBase.IDataBase;

public class Model implements IModel{

    @Override
    public Integer addRow(Product newProduct){

        FDataBase dbFactory = new FDataBase();
        IDataBase dataBase = dbFactory.initDataBase();

        int row = dataBase.addRow(
            newProduct.getName(),
            newProduct.getPrice(),
            newProduct.getDescription()
        );

        return row;
    };

    @Override
    public Boolean checkUser(User user){

        FDataBase dbFactory = new FDataBase();
        IDataBase dataBase = dbFactory.initDataBase();
        return dataBase.isUserCorrect(user.getLogin(), user.getPassword());

    }

    @Override
    public Boolean addUser(User newUser){

        FDataBase dbFactory = new FDataBase();
        IDataBase dataBase = dbFactory.initDataBase();
        return dataBase.createUser(
            newUser.getLogin(), 
            newUser.getPassword(), 
            newUser.getEmail()
        );

    }

    @Override
    public Integer deleteRows(ArrayList<Integer> toDelete){

        FDataBase dbFactory = new FDataBase();
        IDataBase dataBase = dbFactory.initDataBase();
        return dataBase.deleteRows(toDelete);

    }

    @Override
    public ArrayList<Product> getProductsList(){

        FDataBase dbFactory = new FDataBase();
        IDataBase dataBase = dbFactory.initDataBase();
        return Product.GenerateProductList(
            dataBase.selectProducts()
        );
    }

}
