package com.ecommapp.spring.checkout;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentMethodsRequest;
import com.adyen.model.checkout.PaymentMethodsResponse;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.ecommapp.checkout.AdyenVariables;
import com.ecommapp.spring.models.StringResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class AdyenApiController {
	private final Client client;
	private final Checkout checkout;
	public AdyenApiController() {
		client = new Client(AdyenVariables.getAPIKey(),Environment.TEST);
		checkout = new Checkout(client);
		
	}

	
	
	
	@PostMapping("/payment-methods")
	public ResponseEntity<PaymentMethodsResponse> paymentMethods() throws IOException, ApiException {
	        PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
	        paymentMethodsRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
	        paymentMethodsRequest.setCountryCode("NL");
	        paymentMethodsRequest.setShopperLocale("nl-NL");
	        paymentMethodsRequest.setChannel(PaymentMethodsRequest.ChannelEnum.WEB);
//TODO get amount from cart
	        Amount amount = new Amount();
			amount.setCurrency("EUR");
			amount.setValue(1000L);
			paymentMethodsRequest.setAmount(amount);
	        PaymentMethodsResponse response = checkout.paymentMethods(paymentMethodsRequest);
	        return ResponseEntity.ok()
	            .body(response);
		
	}
	
	@PostMapping("/payment")
    public ResponseEntity<PaymentsResponse> payments(@RequestBody PaymentsRequest req, HttpServletRequest request) throws IOException, ApiException {
        PaymentsRequest paymentRequest = new PaymentsRequest();
        paymentRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
        paymentRequest.setCountryCode("NL");
        paymentRequest.setShopperLocale("nl-NL");
        paymentRequest.setChannel(PaymentsRequest.ChannelEnum.WEB);
        String url = "http://localhost:8080/Project1/adyen/checkout/success";
        paymentRequest.setReturnUrl(url);
        String orderRef = UUID.randomUUID().toString();
        paymentRequest.setReference(orderRef);
        paymentRequest.setPaymentMethod(req.getPaymentMethod());
        // required for 3ds2 native flow
   /*     paymentRequest.setAdditionalData(Collections.singletonMap("allow3DS2", "true"));
        paymentRequest.setOrigin(req.getOrigin());
        // required for 3ds2 flow
        paymentRequest.setBrowserInfo(req.getBrowserInfo());
        // required by some issuers for 3ds2
        paymentRequest.setShopperIP(request.getRemoteAddr());
*/
      //TODO get amount from cart
        Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setValue(1000L);
        paymentRequest.setAmount(amount);
        PaymentsResponse response = checkout.payments(paymentRequest);

        return ResponseEntity.ok()
            .body(response);
    }
	
	@PostMapping("/client-key")
	public ResponseEntity<StringResponse> getClientKey(@RequestHeader("referer") String referer) throws IOException, ApiException {
	       
			System.out.println(referer);
	        return ResponseEntity.ok()
	            .body(new StringResponse(AdyenVariables.getClientKey()));
		
	}













////ALL THE TEST STUFF


	@GetMapping("/paymentm-methods-test")
	
	public void testPaymentsMethods(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
		paymentMethodsRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
		paymentMethodsRequest.setCountryCode("NL");
		paymentMethodsRequest.setShopperLocale("nl-NL");
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setValue(1000L);
		paymentMethodsRequest.setAmount(amount);
		paymentMethodsRequest.setChannel(PaymentMethodsRequest.ChannelEnum.WEB);
		try {
			PaymentMethodsResponse paymentMethodsResponse = checkout.paymentMethods(paymentMethodsRequest);
			Gson gson = new Gson();

			String json = gson.toJson(paymentMethodsResponse);
			out.print(json);
			out.flush();
		} catch (ApiException | IOException e) {
			e.printStackTrace();
			out.print("");
			out.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@GetMapping("/payment-test")
	public void testPayments(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
	}
}
