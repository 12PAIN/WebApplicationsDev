
class User{
    constructor({id, login, password, email, name}){
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
    }
};

export{User};