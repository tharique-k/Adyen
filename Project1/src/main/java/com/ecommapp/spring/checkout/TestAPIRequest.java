package com.ecommapp.spring.checkout;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommapp.models.Data;

@Controller
@RequestMapping("test")
public class TestAPIRequest {
	
	/*
	@RequestMapping("/form")
	public String  getForm() {
		System.out.println("+++++++++++++++");
		return "form";
		
	}
	
	@RequestMapping("/showValue")
	public String  processForm(@RequestParam("value") String value, Model model) 
		model.addAttribute("value",value);
		return "show-value";
		
	}
	*/
	
	//// DATA BINDING /////
	
	@RequestMapping("/form")
	public String  getForm(Model model) {
		Data data= new Data();
		model.addAttribute("data",data);
		return "form";
		
	}
	
	@RequestMapping("/showValue")
	public String  processForm(@ModelAttribute("data") Data data ) {
		return "show-value";
	}
	

}
