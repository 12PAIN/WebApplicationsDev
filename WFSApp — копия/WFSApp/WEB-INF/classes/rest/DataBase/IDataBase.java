package rest.DataBase;

import java.util.ArrayList;

public interface IDataBase {
    ArrayList<String> isUserCorrect(String login, String password);
    Boolean createUser(String login, String password, String email);
}
