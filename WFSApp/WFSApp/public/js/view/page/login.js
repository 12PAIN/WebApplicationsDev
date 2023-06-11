import { UserDatasource } from "../../Model/userModel.js";
import {User} from "../../Model/dto/user.js";

class pageLogin{

    constructor(newRouter){
        this.root = undefined;
        this.router = newRouter;
        this.userModel = new UserDatasource();
    }

    mainPageDisplay() {
        this.router.renderPage("mainPage");
    }

    registerButtonClicked() {
        this.router.renderPage("registerPage");
    }

    loginButtonClicked() {
        if (document.getElementById('login').value == '' || document.getElementById('password').value == '') {
            if (document.getElementById('errLogin') != null) {
                document.getElementById('loginDiv').removeChild(document.getElementById('errLogin'));
            }
            let errP = document.createElement('p');
            errP.id = 'errLogin';
            errP.class = 'div-LoginError';
            errP.innerText = 'Ошибка! Введите данные!';
            document.getElementById('loginDiv').appendChild(errP);
            return;
        }
        else{ 
            if (document.getElementById('errLogin') != null) {
                document.getElementById('loginDiv').removeChild(document.getElementById('errLogin'));
            }
            let errP = document.createElement('p');
            errP.id = 'errLogin';
            errP.class = 'div-LoginWarning';
            errP.innerText = 'Пожалуйста подождите!';
            document.getElementById('loginDiv').appendChild(errP);
            this.authQuerry(document.getElementById('login').value, document.getElementById('password').value);
        }
    }

    async authQuerry(login, password) {
        let user = new User({
            id: undefined,
            login: login,
            password: password,
            email: undefined,
            name: undefined
        })

        let response = await this.userModel._authQuery(user);

        let status = response.status;
        response = response.text;

        if (status == 200) {
            localStorage.setItem('WFSAppUserToken', JSON.stringify(response));
            if (document.getElementById('errLogin') != null) {
                document.getElementById('loginDiv').removeChild(document.getElementById('errLogin'));
            }
            let errP = document.createElement('p');
            errP.id = 'errLogin';
            errP.class = 'div-LoginWarning';
            errP.innerText = 'Вы вошли! Загрузка...';
            document.getElementById('loginDiv').appendChild(errP);
            this.renderPage();
            return;
        }
        else if (status == 401) {
            if (document.getElementById('errLogin') != null) {
                document.getElementById('loginDiv').removeChild(document.getElementById('errLogin'));
            }
            let errP = document.createElement('p');
            errP.id = 'errLogin';
            errP.class = 'div-LoginError';
            errP.innerText = 'Ошибка входа! Неправильный логин или пароль.';
            document.getElementById('loginDiv').appendChild(errP);
        }
        else {
            if (document.getElementById('errLogin') != null) {
                document.getElementById('loginDiv').removeChild(document.getElementById('errLogin'));
            }
            let errP = document.createElement('p');
            errP.id = 'errLogin';
            errP.class = 'div-LoginError';
            errP.innerText = 'Ошибка сервера! Попробуйте позже';
            document.getElementById('loginDiv').appendChild(errP);
        }

    }

    loginPageDisplay() {
        
        root.innerHTML = '';

        let div = document.createElement('div');
        div.id = 'loginDiv';
        div.className = 'div-loginForm WrapCenteredInlineBlock';
        let header = document.createElement('p');
        header.innerText = "Добро пожаловать!";
        div.appendChild(header);
        
        let p1 = document.createElement('p');
        let p2 = document.createElement('p');
    
        let inp1 = document.createElement('input');
        let inp2 = document.createElement('input');
    
        inp1.name = 'login';
        inp1.id = 'login';
        inp2.name = 'password';
        inp2.id = 'password';
        inp2.type = 'password';
    
        inp1.placeholder = 'Логин';
        inp2.placeholder = 'Пароль';
    
        p1.appendChild(inp1);
        p2.appendChild(inp2);
    
    
        div.appendChild(p1);
        div.appendChild(p2);
    
    
        let btn1 = document.createElement('button');
        let btn2 = document.createElement('button');
        btn1.textContent = 'Войти';
        btn1.type = 'submit';
        btn2.textContent = 'Зарегистрироваться';
    
    
        let divBtn = document.createElement('div');
        divBtn.id = 'LoginButtons'
        divBtn.className = 'div-twoButtonsContainer';
    
        divBtn.appendChild(btn1);
        divBtn.appendChild(btn2);
    
        div.appendChild(divBtn);
        root.appendChild(div);
    
        btn1.addEventListener("click", this.loginButtonClicked.bind(this));
        inp1.addEventListener("keyup", (function(event) {
            if (event.key === "Enter") {
                this.loginButtonClicked();
            }
        }).bind(this));
    
        inp2.addEventListener("keyup", (function(event) {
            if (event.key === "Enter") {
                this.loginButtonClicked();
            }
        }).bind(this));
        btn2.addEventListener("click", this.registerButtonClicked.bind(this));
    }

    renderPage() {
        if(localStorage.getItem('WFSAppUserToken') == null) {
            this.loginPageDisplay();
        }
        else {
            this.mainPageDisplay();
        }
    }

    _init(rootParam) {
        this.root = rootParam; 
        this.renderPage();
    }

};

export {pageLogin};