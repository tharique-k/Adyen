package com.ecommapp.checkout.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.ecommapp.checkout.AdyenVariables;

/**
 * Servlet implementation class PaymentsRequest
 */
public class PaymentsAPIRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentsAPIRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
		Client client = new Client(AdyenVariables.getAPIKey(),Environment.TEST);
		Checkout checkout = new Checkout(client);
		PaymentsRequest paymentsRequest = new PaymentsRequest();
		paymentsRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
		// STATE_DATA is the paymentMethod field of an object passed from the front end or client app, deserialized from JSON to a data structure.
//		paymentsRequest.setPaymentMethod(STATE_DATA)
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setValue(1000L);
		paymentsRequest.setAmount(amount);
		paymentsRequest.setReference("YOUR_ORDER_NUMBER");
		paymentsRequest.setReturnUrl("https://your-company.com/checkout?shopperOrder=12xy..");
		try {
			PaymentsResponse paymentsResponse = checkout.payments(paymentsRequest);
		} catch (ApiException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
