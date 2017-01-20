package com.testapp.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigthunk.io.BTFileUtils;
import com.bigthunk.web.BTURL2;

@RestController
@RequestMapping("test")
public class TestTxController {

	@RequestMapping(value="tx", method=RequestMethod.GET)
	public String testtx(){
		
		return BTFileUtils.getTextFileFromClassLoader(this, "static/testtx.html");
	}
	
	@RequestMapping(value="doTx", method=RequestMethod.POST)
	public String doTx(@RequestParam String token, @RequestParam String amt) throws Exception{
		
		
		System.out.println("Stripe token is " + token);
		
//		if(true){
//			return "OK";
//		}
		
		String ce = "morganwong50@gmail.com";
		String me = "bigseethruboats@hotmail.com";
		
		String h = "https://tripmoneyhome.herokuapp.com/tripmoney";
		
		String lat = "51.8985986";
		String lng = "-8.4728705";
		
		String cid = "";
		String mid = "";
		String pid = "";
		
		String stripeToken = token;
		String TMToken = "";
		
		BTURL2 getId = new BTURL2(h + "/rest/customers/getID",
				"email", ce,
				"d", "3",
				"hr", "9");
		getId.get();
		cid = getId.getContent();
		cid = cid.substring(0, cid.indexOf(','));
		
		System.out.println("Got CID ! - " + cid);
		
		BTURL2 getMerch = new BTURL2(h + "/rest/merchants/findMerchByLocAndOpenHrs", 
				"lat", lat,
				"lng", lng,
				"dist", "5",
				"time", "17.29",
				"dayOfWeek", "2");
		getMerch.get();
		mid = getMerch.getContent();
		
//		System.out.println("mid is " + mid);
		//"id":"oGv2KtlwOwp63hThmYmERc8Rmt2_L6OuTeZCgCEXm_U_jesDaRSHLQ~~","em
		mid = mid.substring(mid.indexOf("id\"") + 7, mid.indexOf("\",  \""));
		
		System.out.println("Got MID ! - " + mid);
		
		BTURL2 payStart = new BTURL2(h + "/rest/paystation/paymentStart",
				"cid", cid,
				"mid", mid,
				"clat", lat,
				"clng", lng);
		payStart.post();
		pid = payStart.getContent();
		
//		System.out.println("pid is " + pid);
		pid = pid.substring(pid.indexOf("id\"") + 7, pid.indexOf("\",  \""));
		System.out.println("Got PID ! - " + pid);
		//https://tripmoneyhome.herokuapp.com/tripmoney/rest/paystation/paymentStart?cid=gDXS5x-gUfvZl54Kjki7n7a_seyG8VUC2kHQCvRrFRwD52oYxG-Jjw~~&mid=IsaFlVOmdTchC8pLnxOwCMtwHFCaI0BDVujBq3-KFNCasYsA1Y8ijQ~~&clat=51.8986095&clng=-8.4728176
		
		
		
		BTURL2 merchSelect = new BTURL2(h + "/rest/paystation/merchantHasSelectedCustomer",
				"pid", pid);
		merchSelect.post();
//		System.out.println("merch Select content is : " + merchSelect.getContent());
		//https://tripmoneyhome.herokuapp.com/tripmoney/rest/paystation/merchantHasSelectedCustomer?pid=mECm_7dKCoIwjPi74W2cqjIC-a90cWz_Wp9Rs5W8_4YGuDElzMkdZg~~
		
		
//		String amt = "100.00";
//		double ran = Math.random() * 10;
//		int r = (int) ran;
//		switch(r){
//		case 1:
//		case 2:
//		case 3:
//			amt = "35.00";
//			break;
//		case 4:
//		case 5:
//		case 6:
//			amt = "77.50";
//			break;
//		case 7:
//		case 8:
//		case 9:
//			amt = "120.00";
//			break;
//		default:
//			amt = "100.00";
//			break;
//		}
		
		
		BTURL2 addBill = new BTURL2(h + "/rest/paystation/addBillToPaystation",
				"pid", pid,
				"amt", amt,
				"treatAmt", "10",
				"treatType", "local",
				"customerConfirmed", "false");
		addBill.post();
//		System.out.println("add Bill content is : " + addBill.getContent());
		//https://tripmoneyhome.herokuapp.com/tripmoney/rest/paystation/addBillToPaystation?pid=z9jwjYKnf0xaRvoptt0nnrN9ilt6YMOxZepxIQNOn1dPPlPstG5caA~~
		//&amt=100.00&treatAmt=10&treatType=local&customerConfirmed=true&
		
		
		BTURL2 getTMToken = new BTURL2(h + "/rest/paystation/getPaymentToken");
		getTMToken.post();
		TMToken = getTMToken.getContent();
		System.out.println("TMToken is " + TMToken);
		
		
		BTURL2 doPayment = new BTURL2(h + "/rest/paystation/customerPaying",
				"pid", pid,
				"stripeToken", stripeToken,
				"paymentToken", TMToken);
		doPayment.post();
//		System.out.println("do Payment content is : " + doPayment.getContent());
		
		System.out.println("PAYMENT COMPLETE  !!");
		
		return "OK";
	}
	
}
