package com.djex.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SampleController {

	static Logger LOGGER = LoggerFactory.getLogger(SampleController.class.getName());

	@RequestMapping("/")
	public String index(Model model) {
		Record zapis = new Record();
		model.addAttribute("zapis", zapis); // first member is an attribute,
		// it can be named as we like
		// and it connects with the view,
		// the second attribute is the
		// message itself, or an object

		return "hello"; // connecting and rendering the hello template
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("fileName") String fileName,
			@RequestParam("fileDescription") String fileDescription,
			@RequestParam("fileUpload") MultipartFile fileUpload) {

		BufferedOutputStream stream = null;
		if (!fileUpload.isEmpty()) {
			try {
				byte[] bytes = fileUpload.getBytes();
				stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
				stream.write(bytes);

				LOGGER.info("SUCCESSFULLY UPLOADED: " + fileName);
				LOGGER.info("FILE DESCRIPTION: " + fileDescription);

				return "successful upload of " + fileName;
			} catch (Exception e) {
				return "You failed to upload " + fileName + " => " + e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception ex) {
					}
				}
			}
		} else {
			return "You failed to upload " + fileName + " because the file was empty.";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "upload";
	}
}