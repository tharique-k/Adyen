package com.ecommapp.checkout;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentMethod;
import com.adyen.model.checkout.PaymentMethodDetails;
import com.adyen.model.checkout.PaymentMethodIssuer;
import com.adyen.model.checkout.PaymentMethodsRequest;
import com.adyen.model.checkout.PaymentMethodsResponse;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.model.checkout.details.VisaCheckoutDetails;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Test {

	public static void main(String[] args) throws ApiException, IOException {
		
		testPayments();

		
	}
	
	public static void testPayments() throws ApiException, IOException {
		
		//Client Config
		Client client = new Client(AdyenVariables.getAPIKey(),Environment.TEST);
		Checkout checkout = new Checkout(client);

		//Amount Object
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setValue(1000L);
		
		//Get Payment Methods
		/*PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
		paymentMethodsRequest.setMerchantAccount("AdyenRecruitmentCOM");
		paymentMethodsRequest.setCountryCode("NL");
		paymentMethodsRequest.setShopperLocale("nl-NL");
		paymentMethodsRequest.setAmount(amount);
		paymentMethodsRequest.setChannel(PaymentMethodsRequest.ChannelEnum.WEB);

		PaymentMethodsResponse paymentMethodsResponse = checkout.paymentMethods(paymentMethodsRequest);
		List<PaymentMethod> paymentMethods = paymentMethodsResponse.getPaymentMethods(); */
		
		//Submit payment request
		
		PaymentsRequest paymentsRequest = new PaymentsRequest();
		paymentsRequest.setMerchantAccount("AdyenRecruitmentCOM");

		// STATE_DATA is the paymentMethod field of an object passed from the front end or client app, deserialized from JSON to a data structure.
//		PaymentMethodDetails STATE_DATA = new VisaCheckoutDetails();
//		STATE_DATA.setType("scheme");
//		paymentsRequest.setPaymentMethod(STATE_DATA);
		String encryptedCardNumber = "test_4111111111111111";
		String encryptedExpiryMonth = "test_03";
		String encryptedExpiryYear = "test_2030";
		String encryptedSecurityCode = "test_737";
		paymentsRequest.addEncryptedCardData(encryptedCardNumber,encryptedExpiryMonth, encryptedExpiryYear, encryptedSecurityCode);
		paymentsRequest.setAmount(amount);
		paymentsRequest.setReference("1234");
		paymentsRequest.setReturnUrl("https://your-company.com/checkout?shopperOrder=12xy..");
		PaymentsResponse paymentsResponse = checkout.payments(paymentsRequest);
		System.out.println(paymentsResponse.toString());
	}
	
	public static void testPaymentMethods() throws ApiException, IOException {
		Client client = new Client(AdyenVariables.getAPIKey(),Environment.TEST);

		Checkout checkout = new Checkout(client);

		PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();

		paymentMethodsRequest.setMerchantAccount("AdyenRecruitmentCOM");

		paymentMethodsRequest.setCountryCode("NL");

		paymentMethodsRequest.setShopperLocale("nl-NL");

		Amount amount = new Amount();

		amount.setCurrency("EUR");

		amount.setValue(1000L);

		paymentMethodsRequest.setAmount(amount);

		paymentMethodsRequest.setChannel(PaymentMethodsRequest.ChannelEnum.WEB);

		PaymentMethodsResponse paymentMethodsResponse = checkout.paymentMethods(paymentMethodsRequest);
		
		List<PaymentMethod> paymentMethods = paymentMethodsResponse.getPaymentMethods();
		Iterator<PaymentMethod> itr = paymentMethods.iterator();
		JsonObject paymentMethodsObject = new JsonObject();
		JsonArray array = new JsonArray();
		PaymentMethod pm;
		while(itr.hasNext()) {
			JsonObject json = new JsonObject();	
			pm =itr.next();
			
			json.addProperty("brand", pm.getBrand());
			JsonArray brands =  new JsonArray();
			Iterator<String> brandsItr = pm.getBrands().iterator();
			while(brandsItr.hasNext()) {
				brands.add(brandsItr.next());
			}
			json.add("brands", brands);
			JsonArray configuration =  new JsonArray();
			for (Map.Entry<String,String> entry : pm.getConfiguration().entrySet()) {
				String conf = entry.getKey()+"="+entry.getValue();
				configuration.add(conf);
			}
			
			json.add("configuration",configuration );
			if (pm.getFundingSource()!=null) {
				json.addProperty("fundingSource", pm.getFundingSource().getValue());
			}
			else {
			json.addProperty("fundingSource", "null");
			}
			if (pm.getGroup()!=null) {
				json.addProperty("group", pm.getGroup().getName());
			}
			else {
			json.addProperty("group", "null");
			}
			
			JsonArray issuers =  new JsonArray();
			Iterator<PaymentMethodIssuer> pmiItr = pm.getIssuers().iterator();
			while(pmiItr.hasNext()) {
				PaymentMethodIssuer pmi = pmiItr.next();
				JsonObject jsObj = new JsonObject();
				jsObj.addProperty("id", pmi.getId());
				jsObj.addProperty("name", pmi.getName());
				issuers.add(jsObj);
			}
			json.add("issuers", issuers);
			
			json.addProperty("name", pm.getName());
			json.addProperty("type", pm.getType());
		}
		paymentMethodsObject.add("paymentMethods",array);
		
		
//		System.out.println("Groups : "+ paymentMethodsResponse.getGroups());
//		System.out.println("One click payment methods : "+paymentMethodsResponse.getOneClickPaymentMethods());
		System.out.println("payment methods : "+paymentMethodsResponse.getPaymentMethods());
//		System.out.println("Storing methods : "+paymentMethodsResponse.getStoringMethods());
//		System.out.println("Store payment methods : "+paymentMethodsResponse.getStoredPaymentMethods());
		
	}

}
