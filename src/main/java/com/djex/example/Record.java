package com.djex.example;

import org.springframework.web.multipart.MultipartFile;

public class Record {

	private MultipartFile fileUpload;
	private String fileName;
	private String fileDescription;

	public String getFileDescription() {
		return fileDescription;
	}

	public String getFileName() {
		return fileName;
	}

	public MultipartFile getFileUpload() {
		return fileUpload;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileUpload(MultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}

}
