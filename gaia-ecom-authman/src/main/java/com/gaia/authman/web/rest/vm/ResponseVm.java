package com.gaia.authman.web.rest.vm;

import com.gaia.authman.common.Constants;
import com.gaia.authman.common.GaiaException;

public class ResponseVm {

	private String code;
	private String message;

	public ResponseVm(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ResponseVm getSuccessVm() {
		return new ResponseVm(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG);
	}

	public static ResponseVm getErrorVm(GaiaException e) {
		return new ResponseVm(e.getCode(), e.getMessage());
	}

}
