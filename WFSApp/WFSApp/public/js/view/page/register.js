import { UserDatasource } from "../../Model/userModel.js";
import {User} from "../../Model/dto/user.js";

class pageRegister{

    constructor(newRouter){
        this.router = newRouter;
        this.root = undefined;
        this.userModel = new UserDatasource();
    }

    loginButtonClicked() {
        this.router.renderPage("loginPage");
    }

    registerButtonClicked(){
        let regexp = /^[\w\d%$:.-]+@\w+\.\w{2,7}$/;
        if (
            document.getElementById('new_login').value == '' || 
            document.getElementById('new_password').value == '' || 
            document.getElementById('new_email').value == '' || 
            document.getElementById('new_name').value == '' ||
            regexp.test(document.getElementById('new_email').value) == false
        ){
            if (document.getElementById('errRegister') != null) {
                document.getElementById('registerDiv').removeChild(document.getElementById('errRegister'));
            }
            let errP = document.createElement('p');
            errP.id = 'errRegister';
            errP.class = 'div-LoginError';
            errP.innerText = 'Ошибка! Данные введены некорректно';
            document.getElementById('registerDiv').appendChild(errP);
            return;
        } else this.registerQuerry(document.getElementById('new_login').value, document.getElementById('new_password').value, document.getElementById('new_email').value, encodeURI(document.getElementById('new_name').value)); 
    }

    async registerQuerry(login,password,email, name) {

        let user = new User({
            id: undefined,
            login: login,
            password: password,
            email: email,
            name: name
        });

        let response = await this.userModel._registerQuery(user);
        let status = response.status;
        response = response.text;

        if (status == 200) {
            if (document.getElementById('errRegister') != null) {
                document.getElementById('registerDiv').removeChild(document.getElementById('errRegister'));
            }
            let errP = document.createElement('p');
            errP.id = 'errRegister';
            errP.class = 'div-LoginWarning';
            errP.innerText = 'Вы успешно зарегистрировались! Вернитесь, чтобы войти';
            document.getElementById('registerDiv').appendChild(errP);
            return;
        } else if (status == 401) {
            if (document.getElementById('errRegister') != null) {
                document.getElementById('registerDiv').removeChild(document.getElementById('errRegister'));
            }
            let errP = document.createElement('p');
            errP.id = 'errRegister';
            errP.class = 'div-LoginError';
            errP.innerText = 'Пользователь уже существует! Попробуйте другой логин или почту.';
            document.getElementById('registerDiv').appendChild(errP);
            return;
        } else {
            if (document.getElementById('errRegister') != null) {
                document.getElementById('registerDiv').removeChild(document.getElementById('errRegister'));
            }
            let errP = document.createElement('p');
            errP.id = 'errRegister';
            errP.class = 'div-LoginError';
            errP.innerText = 'Ошибка сервера, попробуйте позже';
            document.getElementById('registerDiv').appendChild(errP);
            return;
        }
    }

    registerPageDisplay() {
        root.innerHTML = '';
        let div = document.createElement('div');
        div.id = 'registerDiv';
        div.className = 'div-loginForm WrapCenteredInlineBlock';
        
        let p1 = document.createElement('p');
        let p2 = document.createElement('p');
        let p3 = document.createElement('p');
        let p4 = document.createElement('p');
    
        let inp1 = document.createElement('input');
        let inp2 = document.createElement('input');
        let inp3 = document.createElement('input');
        let inp4 = document.createElement('input');
    
        inp1.name = 'new_login';
        inp1.id = 'new_login';

        inp2.name = 'new_password';
        inp2.id = 'new_password';
        inp2.type = 'password';

        inp3.name = 'new_email';
        inp3.id = 'new_email';
        
        inp4.name = 'new_name';
        inp4.id = 'new_name';
    
        inp1.placeholder = 'Новый логин';
        inp2.placeholder = 'Пароль';
        inp3.placeholder = 'Ваша почта';
        inp4.placeholder = 'Ваше имя';

        p1.appendChild(inp1);
        p2.appendChild(inp2);
        p3.appendChild(inp3);
        p4.appendChild(inp4);
    
        div.appendChild(p1);
        div.appendChild(p2);
        div.appendChild(p3);
        div.appendChild(p4);
    
        let btn1 = document.createElement('button');
        let btn2 = document.createElement('button');
        btn1.textContent = 'Зарегистрироваться';
        btn2.textContent = 'Вернуться';
    
        let divBtn = document.createElement('div');
        divBtn.id = 'RegisterButtons'
        divBtn.className = 'div-twoButtonsContainer';
    
        divBtn.appendChild(btn1);
        divBtn.appendChild(btn2);
    
        div.appendChild(divBtn);
        root.appendChild(div);
    
        btn1.addEventListener("click", this.registerButtonClicked.bind(this));
        btn2.addEventListener("click", this.loginButtonClicked.bind(this));
    
        inp1.addEventListener("keypress", ((event) => {
            if (event.key === "Enter") {
                this.registerButtonClicked.bind(this);
            }
        }).bind(this));
    
        inp2.addEventListener("keypress", ((event) => {
            if (event.key === "Enter") {
                this.registerButtonClicked.bind(this);
            }
        }).bind(this));
    
        inp3.addEventListener("keypress", ((event) => {
            if (event.key === "Enter") {
                this.registerButtonClicked.bind(this);
            }
        }).bind(this));

        inp4.addEventListener("keypress", ((event) => {
            if (event.key === "Enter") {
                this.registerButtonClicked.bind(this);
            }
        }).bind(this));
    }

    renderPage() {
        this.registerPageDisplay();
    }

    _init(rootParam){
        this.root = rootParam; 
        this.renderPage();
    }

}

export {pageRegister};