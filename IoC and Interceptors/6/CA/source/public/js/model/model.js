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

  
    async function _test() {        
        let response  = await fetch('api/test1',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(_students.get())})        
        let status = response.status;        
        if (status !== 200) {
          let error = await response.text();
          throw error;
        }
        let data = await response.json();        
        _students.set(data);
        return data;
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
      async_test: _test
    };
    
    
    
//*********************************************   
}
)();

export {mdl};