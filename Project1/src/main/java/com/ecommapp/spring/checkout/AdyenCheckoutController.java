package com.ecommapp.spring.checkout;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class AdyenCheckoutController {
	
	public AdyenCheckoutController() {
		
	}
	
	
	@GetMapping("/preview")
	public String checkoutPreview() {
		
		return "checkout";
	}
	@GetMapping("/success")
	public String checkoutSuccess() {
		
		return "success";
	}

}
