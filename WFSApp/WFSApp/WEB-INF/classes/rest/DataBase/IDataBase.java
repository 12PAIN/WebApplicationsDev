package rest.DataBase;

import java.util.ArrayList;

public interface IDataBase {
    Boolean isUserCorrect(String login, String password);
    Boolean createUser(String login, String password, String email);
    Integer addRow(String name, Integer price, String description);
    Integer deleteRows(ArrayList<Integer> to_delete);
    ArrayList<ArrayList<String>> selectProducts();
}
