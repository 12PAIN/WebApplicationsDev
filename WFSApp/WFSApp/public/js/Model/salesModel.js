import { Sale } from "./dto/sale.js";


class SalesDatasource{

    constructor(){}

    async _getSalesList(){

        return new Promise( (resolve) => {
            let status;
            fetch('api/sales', {method: 'GET', headers: {'Content-Type': 'application/json;charset=utf-8', 'User-token': localStorage.getItem('WFSAppUserToken')}})
            .then( (response) => {
                status = response.status;
    
                return response.json();
            })
            .then( (data) => {
    
                let salesList = [];
    
    
                for(let sale of data){
                    salesList.push(new Sale(sale));
                }
    
                let result = {
                    status: status,
                    data: salesList
                }
    
                resolve(result);  
            })
        });

    }

    async _addSale(sale){
        return new Promise( (resolve) => {
            let status;
            fetch('api/sales', {method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8', 'User-token': localStorage.getItem('WFSAppUserToken')}, body: JSON.stringify(sale)})
            .then( (response) => {
                status = response.status;
                return response.json()
            })
            .then( (data) => {
                let result = {
                    status: status,
                    data: new Sale(data)
                }
                resolve(result);  
            })
        });
    }

}

export{SalesDatasource};