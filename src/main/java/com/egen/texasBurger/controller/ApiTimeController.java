package com.egen.texasBurger.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.texasBurger.model.ApiMetadata;
import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.PaginationResponse;
import com.egen.texasBurger.repository.ApiTimeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/")
@Log4j2
@Api(value="ApiTime", description  ="Time API Operations related to Application APIS ")
public class ApiTimeController {
	
	@Autowired
	ApiTimeRepository apiRepo;

	@GetMapping(value = "/apitime")
	@ApiOperation(value = "Api execution time filtered on URI and method", response = ApiMetadata.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Api Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  filterAPIByUriAndMethod(@RequestParam(required = false) String uri,@RequestParam(required = false) String method,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size) 
	{
			log.info("Entering /apitime");
        	 Pageable paging = PageRequest.of(page, size);
        	 Page<ApiMetadata> pagedApis = apiRepo.findByUriAndMethodType(uri, method, paging);
        	 PaginationResponse pr = new PaginationResponse(pagedApis.getNumber(),
        			 pagedApis.getTotalElements(),
        			 pagedApis.getTotalPages(),
        			 pagedApis.getContent());
            return new ResponseEntity<>(pr, HttpStatus.OK);
    }
	
	@GetMapping(value = "/apitime/byDate")
	@ApiOperation(value = "Api execution time filtered on Date", response = ApiMetadata.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Api Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  filterAPIByDate(@RequestParam(required = false) LocalDate date,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size) 
	{
        	 Pageable paging = PageRequest.of(page, size);
        	 Page<ApiMetadata> pagedApis = apiRepo.findByDate(date, paging);
        	 PaginationResponse pr = new PaginationResponse(pagedApis.getNumber(),
        			 pagedApis.getTotalElements(),
        			 pagedApis.getTotalPages(),
        			 pagedApis.getContent());
            return new ResponseEntity<>(pr, HttpStatus.OK);
    }

}
