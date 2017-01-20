package com.testapp.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bigthunk.io.BTFileUtils;

@RestController
@RequestMapping("/view")
public class ViewController {
	
	@RequestMapping(value="video", method=RequestMethod.GET)
	public String video(){
		String file = BTFileUtils.getTextFileFromClassLoader(this, "static/video.html");
		return file;
	}
}
