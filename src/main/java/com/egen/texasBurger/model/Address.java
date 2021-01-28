package com.egen.texasBurger.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
	
	@NotBlank
	public String street;
	@NotBlank
	public String city;
	@NotBlank
	public String state;
	@NotBlank
	public String country;
	@NotNull
	public String zipcode;
	@NotNull
	public String phone;
	public Double latitude;
	public Double longitude;
}
