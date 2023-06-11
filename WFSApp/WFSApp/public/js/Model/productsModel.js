import {Product} from "./dto/product.js"

class ProductsDatasource{
    constructor(){}

    _getProductsList(){
        return new Promise( (resolve) => {
            let status;
            fetch('api/products', {method: 'GET', headers: {'Content-Type': 'application/json;charset=utf-8', 'User-token': localStorage.getItem('WFSAppUserToken')}})
            .then( (response) => {
                status = response.status;

                return response.json();
            })
            .then( (data) => {

                let productsList = [];


                for(let product of data){
                    productsList.push(new Product(product));
                }

                let result = {
                    status: status,
                    data: productsList
                }

                resolve(result);  
            })
        });
    }
    
    _addProduct(product){
        return new Promise( (resolve) => {
            let status;
            fetch('api/products/', {method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8', 'User-token': localStorage.getItem('WFSAppUserToken')}, body: JSON.stringify(product)})
            .then( (response) => {
                status = response.status;
                return response.json()
            })
            .then( (data) => {
                let result = {
                    status: status,
                    data: new Product(data)
                }
                resolve(result);  
            })
        });
    }

}


export{ProductsDatasource};