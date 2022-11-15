package rest.Model;

import java.util.ArrayList;

import rest.DataBase.FDataBase;
import rest.DataBase.IDataBase;

public class ModelUser implements IModelUser{

    @Override
    public ArrayList<String> checkUser(User user){

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
}
