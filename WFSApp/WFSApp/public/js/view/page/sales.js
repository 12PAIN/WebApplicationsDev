
import {SalesDatasource} from "../../Model/salesModel.js";

class pageSales{

    constructor(newRouter){
        this.root = undefined;
        this.router = newRouter;
        this.model = new SalesDatasource();
        this.sales = undefined;
        this.displaybleSales = undefined;
    }

    async loginPageDisplay() {
        this.router.renderPage("loginPage");
    }

    async mainPageDisplay(){
        this.router.renderPage("mainPage");
    }

    async createSortInputs(){
        
        let tableDiv = document.getElementById('salesList');


        let sortDiv = document.createElement('div');
        sortDiv.id = 'sortForm';

        let sortInput1Label = document.createElement('label');
        sortInput1Label.innerText = ':ID по возрастанию ';

        let sortInput1 = document.createElement('input');
        sortInput1.id = 'sortInput1';
        sortInput1.type = 'radio';
        sortInput1.name = 'sortInput';
        sortInput1.checked = true;
        sortInput1.value = 'noSort';

        let sortInput2Label = document.createElement('label');
        sortInput2Label.innerText = ':Цена по возрастанию ';
        
        let sortInput2 = document.createElement('input');
        sortInput2.id = 'sortInput2';
        sortInput2.type = 'radio';
        sortInput2.name = 'sortInput';
        sortInput2.value = 'sortPriceUp';

        let sortInput3Label = document.createElement('label');
        sortInput3Label.innerText = ':Цена по убыванию ';
        
        let sortInput3 = document.createElement('input');
        sortInput3.id = 'sortInput3';
        sortInput3.type = 'radio';
        sortInput3.name = 'sortInput';
        sortInput3.value = 'sortPriceDown';

        sortDiv.appendChild(sortInput1);
        sortDiv.appendChild(sortInput1Label);
        
        
        sortDiv.appendChild(sortInput2);
        sortDiv.appendChild(sortInput2Label);
        
        sortDiv.appendChild(sortInput3);
        sortDiv.appendChild(sortInput3Label);

        tableDiv.appendChild(sortDiv);

        sortInput1.addEventListener('change', this.updateWithSort.bind(this));
        sortInput2.addEventListener('change', this.updateWithSort.bind(this));
        sortInput3.addEventListener('change', this.updateWithSort.bind(this));

        return;

    }


    async createPriceFilter(){
        
        let tableDiv = document.getElementById('salesList');

        let filterDiv = document.createElement('div');
        filterDiv.id = 'priceFilter';

        let filterInput1 = document.createElement('input');
        filterInput1.id = 'priceFilterMin';
        filterInput1.placeholder = 'Мин. цена';

        let filterInput2 = document.createElement('input');
        filterInput2.id = 'priceFilterMax';
        filterInput2.placeholder = 'Макс. цена';

        filterInput1.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.updateWithSort.bind(this)();
            };
        }).bind(this))

        filterInput2.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.updateWithSort.bind(this)();
            };
        }).bind(this))

        let btnApplyFilter = document.createElement('button');
        btnApplyFilter.id = 'applyFilterButton';
        btnApplyFilter.textContent = 'Применить';
        btnApplyFilter.addEventListener('click',this.updateWithSort.bind(this));

        filterDiv.appendChild(filterInput1);
        filterDiv.appendChild(filterInput2);
        filterDiv.appendChild(btnApplyFilter);

        tableDiv.appendChild(filterDiv);

        return;

    }

    async getSalesList() {

        let response = await this.model._getSalesList();

        let status = response.status;

        if(status == 401) {
            localStorage.removeItem('WFSAppUserToken');
            if(document.getElementById("salesList") != null) root.removeChild(document.getElementById("salesList"));
            this.renderPage();
            return;
        } else if (status == 200) {
            
            this.sales = response.data;

        } else {

            alert("Сервер недоступен, попробуйте позже!");
            if(document.getElementById("productList") != null){
                root.removeChild(document.getElementById("productList"));
            }
        }
    }


    async updateWithSort(){
        
        ///// Сортировка по цене или ID /////

        let sortType = document.getElementById('sortForm').getElementsByTagName('input');

        for(let input of sortType){
            if( input.checked == true){
                sortType = input.value;
                break;
            }
        }

        if(sortType == 'noSort'){
            this.sales.sort((a, b) => a.id > b.id ? 1 : -1);
        }

        if(sortType == 'sortPriceUp'){
            this.sales.sort((a, b) => a.productId.price > b.productId.price ? 1 : -1);
        }

        if(sortType == 'sortPriceDown'){
            this.sales.sort((a, b) => a.productId.price <= b.productId.price ? 1 : -1);
        }
        /////   /////




        ///// Фильтр по цене /////
        let priceFilterMin = document.getElementById('priceFilterMin').value;

        if(priceFilterMin == '') priceFilterMin = -1;
        else priceFilterMin = Number.parseInt(priceFilterMin);

        let priceFilterMax = document.getElementById('priceFilterMax').value;

        if(priceFilterMax == '') priceFilterMax = -1;
        else priceFilterMax = Number.parseInt(priceFilterMax);

        if(priceFilterMin == -1 && priceFilterMax == -1) this.displaybleSales = this.sales;
        if(priceFilterMin != -1 && priceFilterMax == -1) this.displaybleSales = this.sales.filter((sale)=> sale.productId.price >= priceFilterMin);
        if(priceFilterMin == -1 && priceFilterMax != -1) this.displaybleSales = this.sales.filter((sale)=> sale.productId.price <= priceFilterMax);
        if(priceFilterMin != -1 && priceFilterMax != -1) this.displaybleSales = this.sales.filter((sale)=> sale.productId.price <= priceFilterMax && sale.productId.price >= priceFilterMin);
        /////   /////

        await this.updateTable();
        await this.updateStats();
        return;
    }

    async createTable(){
        
        let tableDiv = document.getElementById('salesList');
        
        let table = document.createElement('table');
        table.className = "table-mainPageDisplay WrapCenteredInlineBlock";
        table.id = 'salesTable';

        tableDiv.appendChild(table);
    }

    async createMainDivTable(){

        if(document.getElementById("salesList") != null){
            this.localroot.removeChild(document.getElementById("salesList"));
        }

        let productListMenu = document.createElement('div');
        productListMenu.id = 'salesList';
        productListMenu.className = 'productList';
        
        this.localroot.appendChild(productListMenu);

    }

    async createStatsStr(){
        let salesDiv = document.getElementById('salesList');

        let statsDiv = document.createElement('statsDiv');
        statsDiv.id = 'statsDiv';

        let p = document.createElement('text');
        p.innerText = 'Сумма продаж: '

        let pSumm = document.createElement('text');
        pSumm.id = 'statsSumm';
        
        let p1 = document.createElement('text');
        p1.innerText = 'Количество: '

        let pCount = document.createElement('text');
        pCount.id = 'statsCount';

        statsDiv.appendChild(p);
        statsDiv.appendChild(pSumm);
        statsDiv.appendChild(p1);
        statsDiv.appendChild(pCount);

        salesDiv.appendChild(statsDiv);
    }

    async updateStats(){

        let statsSumm = document.getElementById('statsSumm');

        let summ = (()=>{
            let summtmp = 0;
            for(let item of this.displaybleSales){
                summtmp += item.productId.price;
            }
            return summtmp;
        })();

        let priceStrTmp = summ.toString();
        let priceStr = '';  

        for(let i = priceStrTmp.length; i > 0; i-= 3){
            priceStr = priceStrTmp.substring(i, i-3) + ' ' + priceStr;
        }


        statsSumm.innerText = priceStr;

        let statsCount = document.getElementById('statsCount');

        statsCount.innerText = this.displaybleSales.length;

    }

    async updateTable(){

        let sales = this.displaybleSales;

        let table = document.getElementById('salesTable');
        
        if(table == undefined && table == null){
            await this.createTable();
        }

        table.innerHTML = '';

        let th = document.createElement('tr');
        let tdh1 = document.createElement('th');
        tdh1.innerText = "ID";
        let tdh2 = document.createElement('th');
        tdh2.innerText = "Имя";
        let tdh3 = document.createElement('th');
        tdh3.innerText = "Фамилия";
        let tdh4 = document.createElement('th');
        tdh4.innerText = "Название товара";
        let tdh5 = document.createElement('th');
        tdh5.innerText = "Цена";
        let tdh6 = document.createElement('th');
        tdh6.innerText = "Описание"
    
    
        th.appendChild(tdh1);
        th.appendChild(tdh2);
        th.appendChild(tdh3);
        th.appendChild(tdh4);
        th.appendChild(tdh5);
        th.appendChild(tdh6);
        table.appendChild(th);

        for(let item of sales){
        
            let tr = document.createElement('tr');
            let td1 = document.createElement('td');
            td1.innerText = item.id;
            let td2 = document.createElement('td');
            td2.innerText = item.customerName;

            let td3 = document.createElement('td');
            td3.innerText = item.customerLastName;

            
            let td4 = document.createElement('td');
            td4.innerText = item.productId.name;
            let td5 = document.createElement('td');

            let priceStrTmp = item.productId.price.toString();
            let priceStr = '';  

            for(let i = priceStrTmp.length; i > 0; i-= 3){
                priceStr = priceStrTmp.substring(i, i-3) + ' ' + priceStr;
            }

            td5.innerText = priceStr;


            let td6 = document.createElement('td');
            td6.innerText = item.productId.description;

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            table.appendChild(tr);
        };

        
    }

    async salesPageDisplay() {

        this.root.innerHTML = '';

        let salespage = document.createElement('div');
        salespage.id = 'salesPage';
    
        let btn_exit = document.createElement('button');
        btn_exit.className = "ExitButton";
    
        btn_exit.textContent = 'Выйти';
        btn_exit.addEventListener("click", () => {
            localStorage.removeItem('WFSAppUserToken');
            this.loginPageDisplay();
        });

        let header = document.createElement('div');
        header.className = 'divHelloUser';
        let p = document.createElement('p');
        p.innerText = 'Привет, ' + decodeURI(JSON.parse(localStorage.getItem('WFSAppUserToken')).payload.name) + '!';


        let btn_mainPage = document.createElement('button');
        btn_mainPage.className = "SalesPageButton";

        btn_mainPage.textContent = 'Товары';
        btn_mainPage.addEventListener("click", () => {
            this.mainPageDisplay();
        });
    
        header.appendChild(p);

        salespage.appendChild(btn_exit);
        salespage.appendChild(btn_mainPage);
        salespage.appendChild(header);
        this.root.appendChild(salespage);

        this.localroot = salespage;

        await this.createMainDivTable();
        await this.createSortInputs();
        await this.createPriceFilter();
        await this.createStatsStr();
        await this.createTable();
        await this.getSalesList();
        await this.updateWithSort();
        await this.updateStats();

    }

    renderPage() {
        if(localStorage.getItem('WFSAppUserToken') == null){
            this.loginPageDisplay();
        }
        else {
            this.salesPageDisplay();
        }
    }

    _init(rootParam) {
        this.root = rootParam; 
        this.renderPage();
    }

};

export {pageSales};
    


    