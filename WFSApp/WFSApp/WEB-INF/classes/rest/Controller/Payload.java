package rest.Controller;

public class Payload {
    private String login;
    private String email;

    public static Payload generatePayload(String login, String email){
        Payload payload = new Payload();
        payload.setLogin(login);
        payload.setEmail(email);
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
