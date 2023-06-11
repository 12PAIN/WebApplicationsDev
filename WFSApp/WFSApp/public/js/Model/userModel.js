
class UserDatasource{
    constructor(){}

    _authQuery(user){
        return new Promise( (resolve) => {
            let status;
            fetch('api/users/auth',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(user)})
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


    _registerQuery(user){
        return new Promise( (resolve) => {
            let status;
            fetch('api/users/',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(user)})
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

export {UserDatasource};