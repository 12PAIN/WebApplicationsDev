package rest.Controller;

import rest.Model.User;

public class Payload {
    private String login;
    private String email;

    public static Payload generatePayload(User user){
        Payload payload = new Payload();
        payload.setLogin(user.getLogin());
        payload.setEmail(user.getEmail());
        return payload;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return this.login;
    }

}
