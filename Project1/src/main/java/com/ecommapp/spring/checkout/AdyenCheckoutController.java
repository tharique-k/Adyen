package com.ecommapp.spring.checkout;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.adyen.model.checkout.PaymentsDetailsRequest;
import com.adyen.model.checkout.PaymentsDetailsResponse;
import com.adyen.service.exception.ApiException;

@Controller
@RequestMapping("/checkout")
public class AdyenCheckoutController {
	
	public AdyenCheckoutController() {
		
	}
	
	@GetMapping("/preview")
	public String checkoutPreview(HttpServletRequest request) {
		if(request.getSession()!=null) {
		return "checkout";
		}
		else {
			return "logged_out";
		}
		
	}
	
	
	
	@GetMapping("/success")
	public String checkoutSuccess() {
		
		return "success";
	}
	@GetMapping("/peding")
	public String checkoutPending() {
		
		return "pending";
	}
	@GetMapping("/failed")
	public String checkoutFailed() {
		
		return "failed";
	}
	@GetMapping("/error")
	public String checkoutError() {
		
		return "error";
	}
}
