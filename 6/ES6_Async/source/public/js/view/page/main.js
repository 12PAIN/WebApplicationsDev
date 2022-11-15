import {mdl as model} from '../../model/model.js'

export default  (function() {
//*********************************************  

let root = undefined;

//Local state (Begin)
let pingData = undefined;
let testData = undefined;
//Local state (End)

function _ping() {  
  let data = model.ping();
  if (pingData == undefined) {
    pingData = '';    
  }
  pingData = pingData + ' ' + data;
  _render();
}


async function _async_test() {
  testData = JSON.stringify(await model.async_test());
  _render(); 
}
function _test_callback(data) {      
  testData = JSON.stringify(data)
  _render(); 
}
function _test() {
  //model.async_test(_test_callback);    
  _async_test();
}

function _render() {
  
  let _pingData = pingData;
  if (_pingData == undefined) {
      _pingData = 'Wait for ping ...';
  }  
  
  let _testData = testData;
  if (_testData == undefined) {
    _testData = 'Wait for test ...';
  }
  
  root.innerHTML = ''+
      '<input id="buttonPing" type="button" value="ping">' +
      '<br>' +
      
      '<div id="divPing">' +
         _pingData +
      '</div>' +                       
		
	  '<br>' +
	  '<br>' +
	  '<br>' +
		
      '<input id="buttonTest" type="button" value="test">' +	   
      '<br>' +
      
      '<div id="divTest">' +
        _testData +
      '</div>' +
  '';        

  let bPing = document.getElementById('buttonPing');      
  bPing.addEventListener('click',_ping);

  let bTest= document.getElementById('buttonTest');      
  bTest.addEventListener('click',_test);
}    



function _init(_root) {
   root = _root; 
   _render();
}

return {
  render: _init  
 };



//*********************************************   
}
)();