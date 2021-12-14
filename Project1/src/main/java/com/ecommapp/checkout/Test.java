package com.ecommapp.checkout;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentMethod;
import com.adyen.model.checkout.PaymentMethodIssuer;
import com.adyen.model.checkout.PaymentMethodsRequest;
import com.adyen.model.checkout.PaymentMethodsResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Test {

	public static void main(String[] args) throws ApiException, IOException {

		Client client = new Client(Consts.X_API_KEY,Environment.TEST);

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
