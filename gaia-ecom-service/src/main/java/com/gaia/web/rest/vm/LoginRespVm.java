package com.gaia.web.rest.vm;

public class LoginRespVm {

	private String validate;

	private String message;

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getMessage() {
		return message;
	}

	public LoginRespVm(String validate, String message) {
		super();
		this.validate = validate;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
