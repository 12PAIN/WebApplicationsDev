package rest.model.user.out;

import rest.model.dto.User;
import rest.model.user.in.IUserData;

public interface IUserModel {
    public User checkUser(User user);
    public Boolean addUser(User newUser);
    public void setDataSource(IUserData datasource);
}
