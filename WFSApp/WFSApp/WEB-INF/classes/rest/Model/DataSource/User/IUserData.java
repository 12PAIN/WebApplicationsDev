package rest.Model.DataSource.User;

import java.util.ArrayList;

public interface IUserData {
    ArrayList<String> checkUser(String login, String password);
}
