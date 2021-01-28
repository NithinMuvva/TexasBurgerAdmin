package com.egen.texasBurger.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.texasBurger.model.Menu;
import com.egen.texasBurger.model.PaginationResponse;
import com.egen.texasBurger.model.Reservation;
import com.egen.texasBurger.service.LocationService;
import com.egen.texasBurger.service.impl.ReservationServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/")
@Api(value="Reservations", description  ="API Operations related to Reservations of Restuarants ")
@Log4j2
public class ReservationController {

	@Autowired
    ReservationServiceImpl resService;
	
	@Autowired
	LocationService locationService;
	
	@GetMapping("/reservations/open/{locationId}")
	@ApiOperation(value = "Get all Reservations", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getOpenReservations(@PathVariable("locationId") String locID){
        return new ResponseEntity<>(resService.getOpenReservations(locID,LocalDate.now()), HttpStatus.OK);
    }
	
	@GetMapping("/reservations")
	@ApiOperation(value = "Get Reservations by fullname", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getAllReservationByFullName(@RequestParam(required = false) String fullname,@RequestParam(required = false) String status,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size){
		 log.info("Entering /reservations api method");
		 Pageable paging = PageRequest.of(page, size);
    	 Page<Reservation> pagedLocations = resService.findByFullNameAndStatus(fullname,status, paging);
    	 PaginationResponse pr = new PaginationResponse(pagedLocations.getNumber(),
 				pagedLocations.getTotalElements(),
 				pagedLocations.getTotalPages(),
 				pagedLocations.getContent());
        return new ResponseEntity<>(pr, HttpStatus.OK);
    }
	
	@GetMapping("/reservations/bulk")
	@ApiOperation(value = "Get all Reservations", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getAllReservations(){
		return new ResponseEntity<>(resService.findAllReservations(), HttpStatus.OK);
    }
	
	@PostMapping("/reservations/bulk")
	@ApiOperation(value = "Post all Reservations", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 404, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public  ResponseEntity<?> saveAllReservations(@RequestBody List<Reservation> reservations){
        	resService.saveReservations(reservations);
            return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@DeleteMapping("reservations/bulk")
	@ApiOperation(value = "Delete all Reservations", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllReservations(){
        	resService.deleteAllReservations();
            return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("/reservations/{id}")
	@ApiOperation(value = "Get Reservation by id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getReservationById(@PathVariable("id") String id ){
            return new ResponseEntity<>(resService.findById(id), HttpStatus.OK);
    }
	
	@PostMapping("/reservations")
	@ApiOperation(value = "Post Reservation", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 404, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public  ResponseEntity<?> saveReservations(@RequestBody Reservation reservation){
        	resService.saveReservation(reservation);
            return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PutMapping("/reservations/{id}")
	@ApiOperation(value = "Update reservation by id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 404, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public  ResponseEntity<?> updateReservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
        	resService.updateReservation(id, reservation);
            return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@DeleteMapping("reservations/{id}")
	@ApiOperation(value = "Delete Reservation by Id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteReservationById(@PathVariable("id") String id){
            resService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
