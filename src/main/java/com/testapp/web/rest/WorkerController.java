package com.testapp.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
public class WorkerController {
	
	
	// working !
	@RequestMapping(value="vid", method=RequestMethod.GET)
	public void vid(HttpServletResponse response) throws Exception{
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = classLoader.getResourceAsStream("static/Customer_V1_0.mp4");
 
        // MIME type of the file
        response.setContentType("application/octet-stream");
        // Response header
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + "Customer_V1_0.mp4" + "\"");
        // Read from the file and write into the response
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
	}
	
	
	// not working...
	@RequestMapping(value="vidDL", method=RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> vidDL(HttpServletResponse response) throws Exception{
		
//		URL u = BTFileUtils.getResourceFromClassLoader(this, "static/Customer_V1_0.mp4");
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fileIn = classLoader.getResourceAsStream("static/Customer_V1_0.mp4");
		
		if(fileIn == null){
			System.out.println("NULL NULL NULL");
		}
		
//		File f = new File("/static/Customer_V1_0.mp4");
//		FileInputStream fileIn = new FileInputStream(f);
		ServletOutputStream out = response.getOutputStream();
		
		byte[] outputByte = new byte[4096];
		//copy binary contect to output stream
		while(fileIn.read(outputByte, 0, 4096) != -1){
			out.write(outputByte, 0, 4096);
		}
		fileIn.close();
		out.flush();
		out.close();
		
//		ResponseEntity<byte[]> re = new ResponseEntity<byte[]>(outputByte, HttpStatus.OK);
		
//		HttpHeaders headers = new HttpHeaders();
//	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	    headers.add("Pragma", "no-cache");
//	    headers.add("Expires", "0");
		
//		return re.ok().headers(headers);
	    return ResponseEntity
	            .ok()
//	            .headers(headers)
	            .contentLength(outputByte.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(new InputStreamResource(fileIn));
	}
}
