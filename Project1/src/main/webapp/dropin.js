/*
function getPaymentMethods()
{
 var servletUrl="./jsonServlet";
 var client; 

 if (window.XMLHttpRequest){ 
     client=new XMLHttpRequest();
 } else {
     client=new ActiveXObject("Microsoft.XMLHTTP");
 }

 client.onreadystatechange=function(){
     if(client.readyState==4&&client.status==200)
     {
        var res=client.responseText;
		return res;	
     }
 };

 client.open("GET",servletUrl,true);
 client.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 client.send();
}

*/
const url =""
async function makePayment(data){
	const res1 = await fetch(url, {
			 	 method: 'POST',
			 	 headers: {
			    'Content-Type': 'application/json',
			 			 },
			  	body: JSON.stringify(data),
				});
	const reponse = await res1.json();
	return reponse;
}

function showFinalResult(res){
	
}


const configuration = {
 paymentMethodsResponse: paymentMethodsResponse, // The `/paymentMethods` response from the server.
 clientKey: "AQEyhmfxKonIYxZGw0m/n3Q5qf3VaY9UCJ14XWZE03G/k2NFikzVGEiYj+4vtN01BchqAcwQwV1bDb7kfNy1WIxIIkxgBw==-JtQ5H0iXtu8rqQMD6iAb33gf2qZeGKGhrMpyQAt9zsw=-3wAkV)*$kP%bCcSf", // Web Drop-in versions before 3.10.1 use originKey instead of clientKey.
 locale: "en-US",
 environment: "test",
 onSubmit: (state, dropin) => {
     // Global configuration for onSubmit
     // Your function calling your server to make the `/payments` request
     makePayment(state.data)
       .then(response => {
         if (response.action) {
           // Drop-in handles the action object from the /payments response
           dropin.handleAction(response.action);
         } else {
           // Your function to show the final result to the shopper
           showFinalResult(response);
         }
       })
       .catch(error => {
         throw Error(error);
       });
   },
 onAdditionalDetails: (state, dropin) => {
   // Your function calling your server to make a `/payments/details` request
   makeDetailsCall(state.data)
     .then(response => {
       if (response.action) {
         // Drop-in handles the action object from the /payments response
         dropin.handleAction(response.action);
       } else {
         // Your function to show the final result to the shopper
         showFinalResult(response);
       }
     })
     .catch(error => {
       throw Error(error);
     });
 },
 paymentMethodsConfiguration: {
   card: { // Example optional configuration for Cards
     hasHolderName: true,
     holderNameRequired: true,
     enableStoreDetails: true,
     hideCVC: false, // Change this to true to hide the CVC field for stored cards
     name: 'Credit or debit card',
     onSubmit: () => {}, // onSubmit configuration for card payments. Overrides the global configuration.
   }
 }
};

fetch("./jsonServlet").then(res => res.json()).then(resp => initconfig(resp));
initconfig = (pmResponse) => {
	configuration.paymentMethodsResponse = pmResponse;
}
