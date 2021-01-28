package com.egen.texasBurger.util;

public enum StatusType {
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	CLOSED("CLOSED"),
	OPEN("OPEN");
	
	StatusType(String code) {
		this.code = code;
	}

	private String code;
	
	public String getCode() {
		return code;
	}
}
