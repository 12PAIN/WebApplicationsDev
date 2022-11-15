function loadImage(img,url) {
  return  new Promise( (resolve) => {
	                        img.onload = () => { resolve(url); }										  
				               img.src = url;
                     }); 			 
}

function event_load(event) {	
   console.log("Image loaded: " + event.target.name);
}

function test() {        	
   console.log("Test begin...");
   

   
   
   
   //1
   console.log("Parallel loading...");
   document.i1.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_8eb56_842bba74_XL.jpg";
   document.i2.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8c_fefaa1d2_XL.jpg";	  
   document.i3.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8f_beecb6d9_XL.jpg"; 
   
   
   
   /*
   //2
   console.log("Consistent loading by callback...");
   document.i3.onload = (event)=> {
	  event_load(event); 
	  document.i1.onload = (event)=> {
		 event_load(event);  
		 document.i2.onload = (event) => { event_load(event); }		 
		 document.i2.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8f_beecb6d9_XL.jpg"; 
	  };
	  document.i1.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8c_fefaa1d2_XL.jpg";	  
   };
   document.i3.src = "https://bipbap.ru/wp-content/uploads/2017/10/0_8eb56_842bba74_XL.jpg";
   */
   
    
   /*
   //3	
   console.log("Consistent loading by promise...");
   let img1 = document.i1;
   let url1 = "https://bipbap.ru/wp-content/uploads/2017/10/0_8eb56_842bba74_XL.jpg";
   let img2 = document.i2;
   let url2 = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8c_fefaa1d2_XL.jpg";
   let img3 = document.i3;
   let url3 = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8f_beecb6d9_XL.jpg";
   
   console.log("Start 1...");
   loadImage(img1,url1)
      .then( (url)=>{console.log(url + " loaded. Start 2..."); return loadImage(img2,url2);} )
      .then( (url)=>{console.log(url + " loaded. Start 3..."); return loadImage(img3,url3);} )
      .then( function(url) {console.log(url + " loaded. All loaded!")} )      
   */

   //*********************************************************   
   
   /*
   //3.1
   new Promise( (resolve) => {
      setTimeout( () => {
         console.log("Before resolve");
         resolve(1);
         console.log("After resolve");
      },0);
   })
  .then( (result) => {console.log("Then0: " + result); return result}) 
  .then( (result) => {setTimeout(()=>{console.log("Then1: " + result)},1000); return result} )
  .then( (result) => {setTimeout(()=>{console.log("Then2: " + result)},0);} )
  */


  /*
  //3.2
  let m = 5;
  let p = new Promise( (resolve,reject) => {
    setTimeout( () => {											 
      if (m!=5) {
       let r = m+1;
       console.log("Before Resolve: " + r);
       resolve(r);
       console.log("After Resolve: " + r);
      } 
      else {
       let r = m-1;	  	  
       console.log("Before Reject: " + r);
       reject(r);
       console.log("After Reject: " + r);
      }
    },0)											                                              
  });

  p.then(  (res)  => {setTimeout(()=> {let res1 = res+1; console.log("Then1: " + res1);},1000)} )
  .then(  (res)  => {setTimeout(()=> {let res2 = res+2; console.log("Then2: " + res2);},0)} )
  //catch( (res)  => {let res3 = res+3; console.log("Catch: " + res3);} )
  .catch( (res)  => {let res3 = res+3; console.log("Catch: " + res3);} )
  */

  //*********************************************************     

   
   
  console.log("Test finish.");
}