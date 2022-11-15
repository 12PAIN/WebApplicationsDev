function loadImage(img,url) {
  return new Promise( (resolve) => {
	            img.onload = () => {resolve(url);}										  
				   img.src = url;
					console.log("In the promise: " + url);
         });	               
}


async function async_test() {
   //await true;	
   
   let test;	
   //test = "no await";
   //test = await new Promise(resolve => resolve("await by new Promise"));
   //test = await Promise.resolve("await by Promise.resolve");
   //test = await "await by auto Promise";
   console.log(test);
   



   img1 = document.i1;
   url1 = "https://bipbap.ru/wp-content/uploads/2017/10/0_8eb56_842bba74_XL.jpg";
   img2 = document.i2;
   url2 = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8c_fefaa1d2_XL.jpg";
   img3 = document.i3;
   url3 = "https://bipbap.ru/wp-content/uploads/2017/10/0_a9e8f_beecb6d9_XL.jpg";
      
   console.log("Start 3...");
   url = await loadImage(img3,url3);      
   console.log(url + " loaded.");
   console.log("Start 2...");
   url = await loadImage(img2,url2);      
   console.log(url + " loaded."); 
   console.log("Start 1...");
   url = await loadImage(img1,url1);
   console.log(url + " loaded.");
   console.log("All loaded!");

   return "OK";  
}

function test() {        	
   console.log("Test begin...");
         
   console.log("Consistent loading by async/await...");
   
   async_test()
   .then( result => console.log(result) );
   
   console.log("Test finish.");         
}