package com.ecommapp.spring.checkout;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentMethodsRequest;
import com.adyen.model.checkout.PaymentMethodsResponse;
import com.adyen.model.checkout.PaymentsDetailsRequest;
import com.adyen.model.checkout.PaymentsDetailsResponse;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.model.nexo.PaymentResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.ecommapp.checkout.AdyenVariables;
import com.ecommapp.models.Cart;
import com.ecommapp.spring.models.StringResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class AdyenApiController {
	private final Client client;
	private final Checkout checkout;
	private HashMap<String,String> paymentsSessionsMap = new HashMap<>();
	public AdyenApiController() {
		client = new Client(AdyenVariables.getAPIKey(),Environment.TEST);
		checkout = new Checkout(client);
		
	}

	
	
	
	@PostMapping("/payment-methods")
	public ResponseEntity<PaymentMethodsResponse> paymentMethods(HttpServletRequest request) throws IOException, ApiException {
	        PaymentMethodsRequest paymentMethodsRequest = new PaymentMethodsRequest();
	        paymentMethodsRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
	        paymentMethodsRequest.setCountryCode("AU");
	        paymentMethodsRequest.setShopperLocale("en-AU");
	        paymentMethodsRequest.setChannel(PaymentMethodsRequest.ChannelEnum.WEB);
	        
	        Amount amount = new Amount();
			amount.setCurrency("AUD");
			Long amountValue;
			HttpSession session = request.getSession();
		
			if(session != null && session.getAttribute("name") != null)  {
				String name = (String) session.getAttribute("name");
				
				//No cart in session - create one
				if(session.getAttribute("cart") == null) {
					amountValue = (long) Cart.getCart(name).getTotal();
				}
				//cart is in session, use it
				else {
					Cart cart = (Cart)session.getAttribute("cart");
					amountValue = (long) cart.getTotal();
				}
			}
			else {
				amountValue =0L;
			}
			/*if (amountValue==null) {
				amountValue =0L;
			}*/
			amount.setValue(amountValue);
			paymentMethodsRequest.setAmount(amount);
			System.out.println("===========================");
			System.out.println("Payment methods API request");
			System.out.println("===========================\n");
			System.out.println("Request : "+ paymentMethodsRequest.toString());
			
	        PaymentMethodsResponse response = checkout.paymentMethods(paymentMethodsRequest);
	        
	        System.out.println("Response : "+ response.toString());
	        return ResponseEntity.ok()
	            .body(response);
		
	}
	
	@PostMapping("/payment")
    public ResponseEntity<PaymentsResponse> payments(@RequestBody PaymentsRequest req, HttpServletRequest request) throws IOException, ApiException {
        PaymentsRequest paymentRequest = new PaymentsRequest();
        paymentRequest.setMerchantAccount(AdyenVariables.getMerchantAccout());
        paymentRequest.setCountryCode("AU");
        paymentRequest.setShopperLocale("en-AU");
        paymentRequest.setChannel(PaymentsRequest.ChannelEnum.WEB);
        String url = "http://localhost:8080/Project1/adyen/api/handleRedirect";
        paymentRequest.setReturnUrl(url);
//        String orderRef = UUID.randomUUID().toString();
        String orderRef = "Tharique_checkoutChallenge";
        paymentRequest.setReference(orderRef);
        paymentRequest.setPaymentMethod(req.getPaymentMethod());
        
        //For 3DS
        Map <String, String> additionalData = new HashMap<>();
        additionalData.put("allow3DS2","true");
        additionalData.put("executeThreeD","true");
        paymentRequest.additionalData(additionalData);
        paymentRequest.setOrigin(req.getOrigin());
        paymentRequest.setBrowserInfo(req.getBrowserInfo());
        paymentRequest.setShopperIP(request.getRemoteAddr());
        Amount amount = new Amount();
		amount.setCurrency("AUD");
		Long amountValue;
		HttpSession session = request.getSession();
	
		if(session != null && session.getAttribute("name") != null)  {
			String name = (String) session.getAttribute("name");
			
			//No cart in session - create one
			if(session.getAttribute("cart") == null) {
				amountValue = (long) Cart.getCart(name).getTotal();
			}
			//cart is in session, use it
			else {
				Cart cart = (Cart)session.getAttribute("cart");
				amountValue = (long) cart.getTotal();
			}
		}
		else {
			amountValue =0L;
		}
		
		amount.setValue(amountValue);
		paymentRequest.setAmount(amount);
		
		System.out.println("===================");
		System.out.println("Payment API request");
		System.out.println("===================\n");
		System.out.println("Request : "+ paymentRequest.toString());
        PaymentsResponse paymentResponse = checkout.payments(paymentRequest);
        System.out.println("PSP Reference : "+paymentResponse.getPspReference());
        System.out.println("Response : "+ paymentResponse.toString());
        if (paymentResponse.getAction() != null && paymentResponse.getAction().getPaymentData() !=null && !paymentResponse.getAction().getPaymentData().isEmpty() ) {
        	paymentsSessionsMap.put(orderRef, paymentResponse.getAction().getPaymentData());
        }

        return ResponseEntity.ok()
            .body(paymentResponse);
    }
	
	@PostMapping("/client-key")
	public ResponseEntity<StringResponse> getClientKey(@RequestHeader("referer") String referer) throws IOException, ApiException {
	       
			System.out.println(referer);
	        return ResponseEntity.ok()
	            .body(new StringResponse(AdyenVariables.getClientKey()));
		
	}



	@GetMapping("/handleRedirect")
    public RedirectView checkoutRedirect(@RequestParam String redirectResult ) throws IOException, ApiException {
        PaymentsDetailsRequest detailsRequest = new PaymentsDetailsRequest();
        if (redirectResult !=null && !redirectResult.isEmpty()) {
        	Map<String, String> redirectMap = new HashMap<>();
        	redirectMap.put("redirectResult",redirectResult);
        	 detailsRequest.setDetails(redirectMap);
        }
       String redirectURL = "../checkout/";
       PaymentsDetailsResponse response = checkout.paymentsDetails(detailsRequest);
       System.out.println("PSP Reference from redirect: "+response.getPspReference());
       
       switch (response.getResultCode()) {
       	
	       case AUTHORISED:
	           redirectURL += "success";
	           break;
	       case PENDING:
	       case RECEIVED:
	           redirectURL += "pending";
	           break;
	       case REFUSED:
	           redirectURL += "failed";
	           break;
	       default:
	           redirectURL += "error";
	           break;
       }
       
        return new RedirectView(redirectURL);
        
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
