package rest.MySqlDatabase;

public class FMySqlDataBase {
    public IMySqlDataBase initDataBase(){
        return DataBase.getInstance();
    }
}
