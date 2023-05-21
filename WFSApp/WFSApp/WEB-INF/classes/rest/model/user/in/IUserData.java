package rest.model.user.in;

import rest.model.dto.User;

public interface IUserData {
    public User getUserData(User getableUser);
    public Boolean addUser(User user);
}
