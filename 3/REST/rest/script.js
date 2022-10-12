var std1 = {
   id: 1,
   name: 'Иванов'
  };

var std2 = {
   id: 2,
   name: 'Петров'
  };
  
var std3 = {
   id: 3,
   name: 'Сидоров'
  };
  
var students = [std1,std2,std3];


function test() { 
  //ES6 example - do not use ES6, need to change to async ES5            
  fetch('./api/test',{method: 'POST', headers: {'Content-Type': 'application/json;charset=utf-8'},body: JSON.stringify(students)})
  .then(function(response) {        
      return response.json();
   })
  .then(function(data) {
	students = data;  
	let d = document.getElementById('divTest');      
    let t = d.firstChild;              
    t.nodeValue = JSON.stringify(students);
  });
  


}



function ping() {                            
  var xhr = new XMLHttpRequest();
    
  var flagAsync = false;
  xhr.open("GET", "api", flagAsync);
   
   
  xhr.send();
  
  
  if (xhr.status !== 200) {  
    consolr.log( "Request error: " + xhr.status + ': ' + xhr.statusText );
  } 
  else { 
    var response = xhr.responseText;   
    var d = document.getElementById("divPing");      
    var t = d.firstChild;            
    t.nodeValue = t.nodeValue + " " + response;
   } 
}