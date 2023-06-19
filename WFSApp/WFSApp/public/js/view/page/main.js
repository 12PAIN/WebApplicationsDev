import {ProductsDatasource } from "../../Model/productsModel.js";
import {Product} from "../../Model/dto/product.js";
import {Sale} from "../../Model/dto/sale.js";
import {SalesDatasource} from "../../Model/salesModel.js";

class pageMain{

    constructor(newRouter){
        this.root = undefined;
        this.router = newRouter;
        this.model = new ProductsDatasource();
        this.salesModel = new SalesDatasource();
        this.products = undefined;
    }

    async loginPageDisplay() {
        this.router.renderPage("loginPage");
    }

    async salesPageDisplay() {
        this.router.renderPage("salesPage");
    }

    async addButtonClicked() {

        let product = new Product({
            name: document.getElementById('ProductName').value,
            id: undefined,
            price: Number.parseInt(document.getElementById('Price').value),
            description: document.getElementById('Description').value,
        })

        if(product.name == undefined || product.name == null || product.name == ''){
            alert("Вы ввели неправильные данные!");

            return;
        }

        if(product.price == undefined || product.price == null || isNaN(product.price)){
            alert("Вы ввели неправильные данные!");

            return;
        }
        
        let response = await this.model._addProduct(product);
        let status = response.status;
        let newProduct = response.data;

        if(status == 200) {
            this.products.push(newProduct);
            await this.updateWithSort();
            return;
        }
        
        if(status == 401) {
            localStorage.removeItem('WFSAppUserToken');
            this.renderPage();
            return;
        }
        
        if(status == 400){
            alert("Вы ввели неправильные данные!");
            return;
        }

        return;
    }

    async saleProduct(product){

        let clearForm = (() => {
            if(document.getElementById('saleForm') != undefined || document.getElementById('saleForm') != null){
                this.localroot.removeChild(document.getElementById('saleForm'));
            }
        }).bind(this);

        if(document.getElementById('customerName').value == '' || document.getElementById('customerName').value == ''){
            alert('Вы не ввели данные о покупателе!');
            clearForm();
            return;
        }

        if(document.getElementById('customerLastName').value == '' || document.getElementById('customerLastName').value == ''){
            alert('Вы не ввели данные о покупателе!');
            clearForm();
            return;
        }

        let newSale = new Sale({
            id: undefined,
            customerName: document.getElementById('customerName').value,
            customerLastName: document.getElementById('customerLastName').value,
            productId: product
        });

        clearForm();

        let response = await this.salesModel._addSale(newSale);

        if(response.status == 200) alert('Продажа зарегистрирована!');
        else{
            alert('Ошибка сервера!');
            return;
        };

        product.saled = 1;

        await this.updateWithSort();
        return;
    }

    async saleButtonClicked(event){

        if(document.getElementById('saleForm') != undefined || document.getElementById('saleForm') != null){
            this.localroot.removeChild(document.getElementById('saleForm'));
        }

        let productId = Number.parseInt(event.srcElement.value);

        let productForSale = this.products.filter((product) => product.id == productId)[0];
        
        let formDiv = document.createElement('div');
        formDiv.id = 'saleForm';
        formDiv.className = 'saleFormMainWrapper WrapCenteredInlineBlock';

        formDiv.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.saleProduct.bind(this)(productForSale);
            };
        }).bind(this))

        let innerContainer = document.createElement('div');
        innerContainer.className = 'saleFormInnerWrapper productList WrapCenteredInlineBlock';

        let textP1 = document.createElement('p');

        let priceStrTmp = productForSale.price.toString();
        let priceStr = '';  

        for(let i = priceStrTmp.length; i > 0; i-= 3){
            priceStr = priceStrTmp.substring(i, i-3) + ' ' + priceStr;
        }

        textP1.innerText = 'Продать товар: ' + productForSale.name + ' стоимостью ' + priceStr;

        let inputName = document.createElement('input');
        inputName.className = 'WrapCenteredInlineBlock';
        inputName.placeholder = 'Имя покупателя';
        inputName.id = 'customerName';

        let inputLastName = document.createElement('input');
        inputLastName.className = 'WrapCenteredInlineBlock';
        inputLastName.placeholder = 'Фамилия покупателя';
        inputLastName.id = 'customerLastName';

        let btnDiv = document.createElement('div');
        btnDiv.className = 'div-twoButtonsContainer';

        let buttonSale = document.createElement('button');
        buttonSale.textContent = 'Продать';
        buttonSale.addEventListener('click', (() => {
            this.saleProduct.bind(this)(productForSale)
        }).bind(this));

        let closeButton = document.createElement('button');
        closeButton.textContent = 'Закрыть';
        closeButton.addEventListener('click', (() => {
            if(document.getElementById('saleForm') != undefined || document.getElementById('saleForm') != null){
                this.localroot.removeChild(document.getElementById('saleForm'));
            }
        }).bind(this));

        innerContainer.appendChild(textP1);
        innerContainer.appendChild(inputName);
        innerContainer.appendChild(inputLastName);
        btnDiv.appendChild(buttonSale);
        btnDiv.appendChild(closeButton);
        innerContainer.appendChild(btnDiv);
        formDiv.appendChild(innerContainer);
        
        this.localroot.appendChild(formDiv);

    }

    async updateButtonClicked(){

    }
    
    async getProductList() {

        let response = await this.model._getProductsList();

        let status = response.status;

        if(status == 401) {
            localStorage.removeItem('WFSAppUserToken');
            if(document.getElementById("productList") != null) root.removeChild(document.getElementById("productList"));
            this.renderPage();
            return;
        } else if (status == 200) {
            
            this.products = response.data;

        } else {

            alert("Сервер недоступен, попробуйте позже!");
            if(document.getElementById("productList") != null){
                root.removeChild(document.getElementById("productList"));
            }
        }
    }

    async updateWithSort(){
        
        ///// Сортировка по проданным и непроданным /////
        let saledSortType = document.getElementById('sortSaledForm').getElementsByTagName('input');

        for(let input of saledSortType){
            if( input.checked == true){
                saledSortType = input.value;
                break;
            }
        }

        
        if(saledSortType == 'all') this.displaybleProducts = this.products;

        if(saledSortType == 'saled') this.displaybleProducts = this.products.filter((product)=> product.saled != 0);

        if(saledSortType == 'notSaled') this.displaybleProducts = this.products.filter((product)=> product.saled == 0);
        /////  /////



        ///// Сортировка по цене или ID /////

        let sortType = document.getElementById('sortForm').getElementsByTagName('input');

        for(let input of sortType){
            if( input.checked == true){
                sortType = input.value;
                break;
            }
        }

        if(sortType == 'noSort'){
            this.displaybleProducts.sort((a, b) => a.id > b.id ? 1 : -1);
        }

        if(sortType == 'sortPriceUp'){
            this.displaybleProducts.sort((a, b) => a.price > b.price ? 1 : -1);
        }

        if(sortType == 'sortPriceDown'){
            this.displaybleProducts.sort((a, b) => a.price <= b.price ? 1 : -1);
        }
        /////   /////




        ///// Фильтр по цене /////
        let priceFilterMin = document.getElementById('priceFilterMin').value;

        if(priceFilterMin == '') priceFilterMin = -1;
        else priceFilterMin = Number.parseInt(priceFilterMin);

        let priceFilterMax = document.getElementById('priceFilterMax').value;

        if(priceFilterMax == '') priceFilterMax = -1;
        else priceFilterMax = Number.parseInt(priceFilterMax);

        if(priceFilterMin != -1 && priceFilterMax == -1) this.displaybleProducts = this.displaybleProducts.filter((product)=> product.price >= priceFilterMin);
        if(priceFilterMin == -1 && priceFilterMax != -1) this.displaybleProducts = this.displaybleProducts.filter((product)=> product.price <= priceFilterMax);
        if(priceFilterMin != -1 && priceFilterMax != -1) this.displaybleProducts = this.displaybleProducts.filter((product)=> product.price <= priceFilterMax && product.price >= priceFilterMin);
        /////   /////


        await this.updateTable();
        return;
    }

    async createAdder(){

        let tableDiv = document.getElementById('productList');

        let inpProductName = document.createElement('input');
        inpProductName.className = "inpProductName-mainPageDisplay WrapCenteredInlineBlock";
        inpProductName.placeholder = "Название";
        inpProductName.id = "ProductName";

        inpProductName.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.addButtonClicked.bind(this)();
            };
        }).bind(this))
    
        let inpPrice = document.createElement('input');
        inpPrice.className = "inpPrice-mainPageDisplay WrapCenteredInlineBlock";
        inpPrice.placeholder = "Цена";
        inpPrice.id = "Price";

        inpPrice.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.addButtonClicked.bind(this)();
            };
        }).bind(this))
    
        let inpDescription = document.createElement('input');
        inpDescription.className = "inpDescription-mainPageDisplay WrapCenteredInlineBlock";
        inpDescription.placeholder = "Описание";
        inpDescription.id = "Description";
        inpDescription.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.addButtonClicked.bind(this)();
            };
        }).bind(this))
    
        let btnAdd = document.createElement('button');
        btnAdd.className = "btnAdd-mainPageDisplay WrapCenteredInlineBlock";
        btnAdd.textContent = 'Добавить';
        btnAdd.type = 'submit';
        btnAdd.addEventListener("click", this.addButtonClicked.bind(this));
        btnAdd.addEventListener('keyup', ((event) => {
            if(event.code === 'Enter' ){
                this.addButtonClicked.bind(this)();
            };
        }).bind(this))
    
        let divAdd = document.createElement('div');
        divAdd.className = 'productListAdd';

        divAdd.appendChild(inpProductName);
        divAdd.appendChild(inpPrice);
        divAdd.appendChild(inpDescription);
        divAdd.appendChild(btnAdd);

        tableDiv.appendChild(divAdd);

    }

    async createSortInputs(){
        
        let tableDiv = document.getElementById('productList');


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
        
        let tableDiv = document.getElementById('productList');

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

    async createSortSaledInputs(){
        
        let tableDiv = document.getElementById('productList');


        let sortDiv = document.createElement('div');
        sortDiv.id = 'sortSaledForm';

        let sortInput1Label = document.createElement('label');
        sortInput1Label.innerText = ':Все ';

        let sortInput1 = document.createElement('input');
        sortInput1.id = 'sortSaledInput1';
        sortInput1.type = 'radio';
        sortInput1.name = 'sortSaledInput';
        sortInput1.value = 'all';

        let sortInput2Label = document.createElement('label');
        sortInput2Label.innerText = ':Проданные ';
        
        let sortInput2 = document.createElement('input');
        sortInput2.id = 'sortSaledInput2';
        sortInput2.type = 'radio';
        sortInput2.name = 'sortSaledInput';
        sortInput2.value = 'saled';

        let sortInput3Label = document.createElement('label');
        sortInput3Label.innerText = ':Непроданные ';
        
        let sortInput3 = document.createElement('input');
        sortInput3.id = 'sortSaledInput3';
        sortInput3.type = 'radio';
        sortInput3.name = 'sortSaledInput';
        sortInput3.value = 'notSaled';
        sortInput3.checked = true;

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

    async createTable(){
        
        let tableDiv = document.getElementById('productList');
        
        let table = document.createElement('table');
        table.className = "table-mainPageDisplay WrapCenteredInlineBlock";
        table.id = 'productsTable';

        tableDiv.appendChild(table);
    }

    async createMainDivTable(){

        if(document.getElementById("productList") != null){
            this.localroot.removeChild(document.getElementById("productList"));
        }

        let productListMenu = document.createElement('div');
        productListMenu.id = 'productList';
        productListMenu.className = 'productList';
        
        this.localroot.appendChild(productListMenu);

    }

    async updateTable(){

        let products = this.displaybleProducts;

        let table = document.getElementById('productsTable');
        
        if(table == undefined && table == null){
            await this.createTable();
        }

        table.innerHTML = '';

        let th = document.createElement('tr');
        let tdh1 = document.createElement('th');
        tdh1.innerText = "ID";
        let tdh2 = document.createElement('th');
        tdh2.innerText = "Название";
        let tdh3 = document.createElement('th');
        tdh3.innerText = "Цена";
        let tdh4 = document.createElement('th');
        tdh4.innerText = "Описание";
        let tdh5 = document.createElement('th');
        tdh5.innerText = "Продано?";
        let tdh6 = document.createElement('th');
        tdh6.innerText = "Продать"
    
    
        th.appendChild(tdh1);
        th.appendChild(tdh2);
        th.appendChild(tdh3);
        th.appendChild(tdh4);
        th.appendChild(tdh5);
        th.appendChild(tdh6);
        table.appendChild(th);

        for(let item of products){
        
            let tr = document.createElement('tr');
            let td1 = document.createElement('td');
            td1.innerText = item.id;
            let td2 = document.createElement('td');
            td2.innerText = item.name;
            let td3 = document.createElement('td');

            let priceStrTmp = item.price.toString();
            let priceStr = '';  

            for(let i = priceStrTmp.length; i > 0; i-= 3){
                priceStr = priceStrTmp.substring(i, i-3) + ' ' + priceStr;
            }

            td3.innerText = priceStr;
            let td4 = document.createElement('td');
            td4.innerText = item.description;
            let td5 = document.createElement('td');
            td5.innerText = (item.saled == 0 ? 'Не продано' : 'Продано');

            let td6 = document.createElement('td');

            let saleButton = document.createElement('button');
            saleButton.value = item.id;
            saleButton.innerText = 'Продать';
            if(item.saled != 0) saleButton.disabled = true;
            saleButton.addEventListener('click', this.saleButtonClicked.bind(this))
    
            td6.appendChild(saleButton);

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            table.appendChild(tr);
        };

        
    }

    async mainPageDisplay() {

        root.innerHTML = '';

        let mainpage = document.createElement('div');
        mainpage.id = 'mainPage';
    
        let btn_exit = document.createElement('button');
        btn_exit.className = "ExitButton";
    
        btn_exit.textContent = 'Выйти';
        btn_exit.addEventListener("click", () => {
            localStorage.removeItem('WFSAppUserToken');
            this.loginPageDisplay();
        });

        let btn_salesPage = document.createElement('button');
        btn_salesPage.className = "SalesPageButton";

        btn_salesPage.textContent = 'Продажи';
        btn_salesPage.addEventListener("click", () => {
            this.salesPageDisplay();
        });

        let header = document.createElement('div');
        header.className = 'divHelloUser';
        let p = document.createElement('p');
        p.innerText = 'Привет, ' + decodeURI(JSON.parse(localStorage.getItem('WFSAppUserToken')).payload.name) + '!';
    
        header.appendChild(p);

        mainpage.appendChild(btn_exit);
        mainpage.appendChild(btn_salesPage);
        mainpage.appendChild(header);
        root.appendChild(mainpage);

        this.localroot = mainpage;

        await this.createMainDivTable();
        await this.createAdder();
        await this.createSortInputs();
        await this.createSortSaledInputs();
        await this.createPriceFilter();
        await this.createTable();
        await this.getProductList();
        await this.updateWithSort();

    }

    renderPage() {
        if(localStorage.getItem('WFSAppUserToken') == null){
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

export {pageMain};
