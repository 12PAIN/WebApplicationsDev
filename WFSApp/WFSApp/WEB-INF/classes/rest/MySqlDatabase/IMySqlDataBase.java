package rest.MySqlDatabase;

import java.util.ArrayList;

public interface IMySqlDataBase {
    Boolean isUserCorrect(String login, String password);
    Boolean createUser(String login, String password, String email);
    Integer addRow(String name, int price, String description);
    Integer deleteRows(ArrayList<Integer> to_delete);
    ArrayList<ArrayList<String>> selectProducts();
}
