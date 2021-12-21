/**
 * 
 */
// fetch("https://jsonplaceholder.typicode.com/users").then(res => res.json()).then(resp => console.log(resp));

//const url ="./jsonServlet";
//const url ="./checkout/paymentsAPIRequest";
const paymentMethodsUrl ="./checkout/adyen/paymentm-methods-test";
const paymentsUrl ="./checkout/adyen/payment-test";

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

function showResults(data){
	 document.body.innerHTML =  JSON.stringify(data);
}
function doWork(){
	// fetch("https://jsonplaceholder.typicode.com/users").then(res => res.json()).then(res => makePayment(res)).then(res => showResults(res));
	//fetch(url,{method: 'POST'});
	fetch(paymentsUrl).then(res => res.json()).then(res => showResults(res));
}