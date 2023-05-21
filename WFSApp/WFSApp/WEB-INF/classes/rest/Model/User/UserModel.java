package rest.model.user;

import rest.model.dto.User;
import rest.model.user.in.IUserData;
import rest.model.user.out.IUserModel;

public class UserModel implements IUserModel{

    IUserData datasource;

    public void setDataSource(IUserData datasource){
        this.datasource = datasource;
    }

    @Override
    public User checkUser(User user){
        User userData = datasource.getUserData(user);

        if(userData == null) return null;

        if(userData.getLogin() == null) return userData;
        if(!userData.getLogin().equals(user.getLogin()) || !userData.getPassword().equals(user.getPassword())) userData.setLogin(null);

        return userData;
    }

    @Override
    public Boolean addUser(User newUser){

        Boolean status = datasource.addUser(newUser);
        
        return status;

    }

}
