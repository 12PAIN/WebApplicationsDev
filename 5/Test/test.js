
function Test() {  
  valueLocal = 3; 
  
  this.value = 1;
  console.log(this);  
}

Test.prototype.set   = function(v) { this.value = v; }
Test.prototype.log   = function()  { 
 console.log(this); console.log(valueLocal); console.log(this.valueLocal); console.log(this.value);
}

Test.prototype.getLog = function(){ return () => { this.log(); } }

console.log(Test.prototype);  
console.log("***************************");

Test();

var t = new Test();
console.log("***************************");

t.set(10);

t.log();
console.log("***************************");
t.getLog()();