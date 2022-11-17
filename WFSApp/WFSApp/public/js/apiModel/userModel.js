class User{
    constructor(){
        this.user = undefined;
        this.callbackLink = undefined;
    }
    setUser(usr){
        this.user = usr;
    }
    setCallback(newCallback){
        this.callbackLink = newCallback;
    }

    _authQuery(){
        return new Promise( (resolve) => {
            let status;
            fetch('api/users/auth',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(this.user)})
            .then( (response) => { 
                status = response.status;
                return response.json()
            })
            .then( (data) => {
                let result = {
                    status: status,
                    text: data
                }
                resolve(result);            
            });
        });
    }

    _registerQuery(){
        return new Promise( (resolve) => {
            let status;
            fetch('api/users/',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(this.user)})
            .then( (response) => { 
                status = response.status;
                return response.json()
            })
            .then( (data) => {
                let result = {
                    status: status,
                    text: data
                }
                resolve(result);            
            });
        });
    
    }
}

export {User};