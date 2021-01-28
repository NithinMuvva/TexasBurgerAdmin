package com.egen.texasBurger.model;

import lombok.Data;

@Data
public class MenuItem {

	private String name;
	private Double price;
	private String type;
	private Boolean comboAllowed;
	private Double comboPrice;
}
