package rest.Model;

import java.util.ArrayList;

public interface IModelUser {
    ArrayList<String> checkUser(User user);
    Boolean addUser(User newUser);
}
