package com.egen.texasBurger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location{
	@Id
    @ApiModelProperty(notes = "The database generated product ID")
	private String locationId;
	@ApiModelProperty(notes = "The Location name")
	@NotBlank(message = "location name cannot be null")
	private String name;
	@NotNull
	private Address address; 
	private Set<OpenHours> openHours;
	private String status;
}
