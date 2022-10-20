package rest.Model;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;


public class Token{

    private Payload payload;
    private String crypto;

    public Payload getPayload(){
        return this.payload;
    }

    public String getCrypto(){
        return this.crypto;
    }

    public void setPayload(Payload payload){
        this.payload = payload;
    }

    public void setCrypto(String crypto){
        this.crypto = crypto;
    }

    public static Boolean verifyToken(Token token){
        
        Token checkToken = Token.generateToken(token.getPayload());

        if(checkToken.getCrypto().equals(token.getCrypto())){
            return true;
        } 
        
        return false;

    }

    public static Token generateToken(Payload payload){
        Token token = new Token();
        
        token.setPayload(payload);

        try{
            Jsonb jsonb = JsonbBuilder.create();
            token.setCrypto(jsonb.toJson(payload));
        }catch(JsonbException e){}

        return token;
    }

    public static Token generateToken(User user){
        Token token = new Token();
        Payload payload = Payload.generatePayload(user);
        token.setPayload(payload);

        try{
            Jsonb jsonb = JsonbBuilder.create();
            token.setCrypto(jsonb.toJson(payload));
        }catch(JsonbException e){}

        return token;
    }
}