package com.egen.texasBurger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaginationResponse {
	
	private Integer currentPage;
	private Long totalItems;
	private Integer totalPage; 
	private Object data;

}
