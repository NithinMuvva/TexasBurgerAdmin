package com.egen.texasBurger.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Document(collection = "menu")
public class Menu extends Category{
	@Id
	private String menuid;
	private String menutype;
	private List<MenuItem> menuItems;
	@NotNull
	private String locationId;
	private String status;
}