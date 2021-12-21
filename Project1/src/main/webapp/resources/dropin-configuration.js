
async function initCheckout(){
	
		try{
			const clientKey = await getStringFromBackend('../api/client-key');
			
			//showResults(clientKey); // TEST
			const paymentMethodsResponse = await requestBackend('../api/payment-methods');
			//showJSONResults(paymentMethodsResponse); // TEST
		
			const configuration = {
			 paymentMethodsResponse: paymentMethodsResponse, // The `/paymentMethods` response from the server.
			 clientKey, // Web Drop-in versions before 3.10.1 use originKey instead of clientKey.
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
			 }
			};
			
			const checkout = new AdyenCheckout(configuration);
			
			const dropin = checkout.create('dropin', 
					{
			      openFirstPaymentMethod:false}
				  )
				.mount('#dropin-container');
					
		}
		catch(err){
			console.error(err);
			alert("Error : find more details in the console");
		}
}

initCheckout();

async function requestBackend(url, data){
	
	const res = await fetch(url, {
				method:'POST',
				body : data ? JSON.stringify(data) : '',
				headers : {
					'Content-Type': 'application/json' 	
				}
			});
	return await res.json();
	
}
async function getStringFromBackend(url){
	const res = await fetch(url, {
				method:'POST' 	
				});
	const ret = await res.json();
	return ret.response;
}


async function makePayment(data){
	const res = await fetch("../api/payment", {
			 	 method: 'POST',
			 	 headers: {
			    'Content-Type': 'application/json',
			 			 },
			  	body: JSON.stringify(data),
				});
	return await res.json();

}
//For testing
function showJSONResults(data){
	 document.body.innerHTML =  JSON.stringify(data);
}
function showResults(data){
	 document.body.innerHTML =  "The value is  : " + data;
}