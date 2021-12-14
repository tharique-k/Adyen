/**
 * 
 */
// fetch("https://jsonplaceholder.typicode.com/users").then(res => res.json()).then(resp => console.log(resp));

async function makePayment(data){
	const res = await fetch('./jsonServlet', {
			 	 method: 'POST',
			 	 headers: {
			    'Content-Type': 'application/json',
			 			 },
			  	body: JSON.stringify(data),
				});
	const reponse = await res.json();
	return reponse;
}

function showResults(data){
	 document.body.innerHTML = data;
}