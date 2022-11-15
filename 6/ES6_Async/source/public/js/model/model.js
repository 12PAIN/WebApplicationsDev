const mdl = (function() {
//*********************************************  
    
    //Class Students (Begin)
    class Students {

      constructor() {

        let std1 = {
          id: 2,
          name: 'Иванов'
        };

        let std2 = {
          id: 4,
          name: 'Петров'
        };

        let std3 = {
          id: 6,
          name: 'Сидоров'
        };

        this.students = [std1, std2, std3];
      }

      get() {
        return this.students;
      }

      set(stds) {
        this.students = stds;
      }
    }    
    //Class Students (End)


    let _students = new Students();

    
    function _test(callback) {                     
        fetch('api/test',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(_students.get())})
        .then( (response) => response.json())
        .then( (data) => {          
          _students.set(data);
          callback(data);            
        });
    }
    function _async_test() {
      return new Promise( (resolve) => {
        fetch('api/test',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(_students.get())})
        .then( (response) => response.json())
        .then( (data) => {          
          _students.set(data);
          resolve(data);            
        });
      });  
    }

      
      
    function _ping() {                            
        let xhr = new XMLHttpRequest();
          
        let flagAsync = false;
        xhr.open("GET", "api", flagAsync);
          
        xhr.send();
                
        if (xhr.status !== 200) {            
          return 'Error: ' + xhr.status + ': ' + xhr.statusText;
        } 
        else {
          return xhr.responseText;               
         } 
    }


    return {
      ping: _ping,
      //async_test: _test
      async_test: _async_test
    };
    
    
    
//*********************************************   
}
)();

export {mdl};