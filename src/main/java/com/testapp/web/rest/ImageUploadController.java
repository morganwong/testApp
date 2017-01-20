package com.testapp.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("images")
public class ImageUploadController {
	
	@RequestMapping(value="uploadLargeImage")
	public String uploadLargeImage(){
		
		return "OK";
	}
	
}
