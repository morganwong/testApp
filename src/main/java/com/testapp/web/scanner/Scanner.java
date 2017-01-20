package com.testapp.web.scanner;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bigthunk.web.BTURL2;

@Component
public class Scanner {
	
	@Scheduled(fixedRate=6000000)
	public void scan(){
		System.out.println("!! SCAN START !!");
		
		try {
			BTURL2 u = new BTURL2("https://tripmoneyhome.herokuapp.com/tripmoney/rest/staging/viewCustomers");
			u.get();
			System.out.println(u.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("!! SCAN END !!");
	}
	
}
