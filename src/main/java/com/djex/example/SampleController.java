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

	@RequestMapping("/")
	public String index(Model model) {
		Record zapis = new Record();
		model.addAttribute("zapis", zapis); // prvi clan je atribut,
											// moze se zvati bilo kako,
											// i povezuje se se view-om,
											// dok je drugi sama
											// poruka
											// ili objekat
		return "hello"; // prikazujemo tj. povezujemo sa hello.html templejtom
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "upload";
	}

}