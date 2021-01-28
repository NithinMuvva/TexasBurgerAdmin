package com.egen.texasBurger.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.PaginationResponse;
import com.egen.texasBurger.service.LocationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/")
@Log4j2
@Api(value="Location", description  ="API Operations related to Locations of Texas Burger ")
public class LocationController {

	@Autowired
	LocationService locService;
	
	@GetMapping(value = "/locations")
	@ApiOperation(value = "A Paginated api to filter locations on name and status", response = Location.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  filterLocationsByNameStatus(@RequestParam Map<String, String> parameters,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size) 
	{
		     Page<Location> pagedLocations;
			 log.info("Entering /locations api filterLocationsByNameStatus method ");
        	 Pageable paging = PageRequest.of(page, size);
        	 if(parameters.containsKey("name") && parameters.containsKey("status")) {
        		 pagedLocations = locService.getLocationByStatusAndName(parameters.get("status"), parameters.get("name"), paging);
        	 }else if(parameters.containsKey("name")) {
        		 pagedLocations = locService.findByName(parameters.get("name"), paging);
        	 }else if(parameters.containsKey("status") ) {
        		 pagedLocations = locService.findByStatus(parameters.get("status"), paging);
        	 }else {
        		 pagedLocations = locService.findAllLocation(paging);
        	 }
        	 
        	 PaginationResponse pr = new PaginationResponse(pagedLocations.getNumber(),
     				pagedLocations.getTotalElements(),
     				pagedLocations.getTotalPages(),
     				pagedLocations.getContent());
            return new ResponseEntity<>(pr, HttpStatus.OK);
    }

    @PostMapping(value = "/locations/bulk")
    @ApiOperation(value = "Api to post bulk Locations", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NO Content"),
            @ApiResponse(code = 204, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> saveLocations(@Valid @RequestBody List<Location> location) {
    	locService.saveLocations(location);
    	log.info("Locations are created sucessfully");
        return new ResponseEntity<>("Locations are created sucessfully", HttpStatus.OK);
        
    }
    
    @DeleteMapping(value = "/locations")
    @ApiOperation(value = "Api to delete all Locations", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllLocations() {
    	locService.deleteAllLocation();
    	log.info("Locations is deleted sucessfully");
        return new ResponseEntity<>("Deleted sucessfully", HttpStatus.OK);
    }
    
    @GetMapping(value = "/locations/{id}")
    @ApiOperation(value = "Api to get a Location by id", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  getLocationById(@PathVariable("id") String id){
    	Location location = locService.findByLocationId(id);
    	log.info("Succefullt got location");
    	return new ResponseEntity<>(location, HttpStatus.OK);
    }
	
    @PostMapping(value = "/locations")
    @ApiOperation(value = "Api to create new location", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 204, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  createLocation(@Valid @RequestBody Location location) {
    	locService.saveLocation(location);
    	log.info("Location saved sucessfully");
        return new ResponseEntity<>("Location saved sucessfully", HttpStatus.OK);
    }
    
    @PutMapping(value = "/locations/{id}")
    @ApiOperation(value = "Api to update a location by id", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 204, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  updateLocationById(@PathVariable("id") String id, @Valid @RequestBody Location location) {
    	Location updateLoc = locService.updateLocation(id, location);
    	log.info("Location update sucessfully");
		return new ResponseEntity<>(updateLoc, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/locations/{id}")
    @ApiOperation(value = "Api to delete a location by id", response = Location.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  updateLocationById(@PathVariable String id) {
    	locService.deleteLocationById(id);
    	log.info("Location is deleted sucessfully");
		return new ResponseEntity<>("Location is deleted sucessfully", HttpStatus.OK);
    }
    
	
    @GetMapping(value = "/location/nearby")
    @ApiOperation(value = "Api to get nearby location with given current location",response = Location.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Location Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error") }) 
    public ResponseEntity<?> searchNearByLocation(@RequestParam(name = "longitude") Double longitude, @RequestParam(name = "latitude") Double latitude) { 
	    	Location loc = locService.findNearByLocation(latitude, longitude);
	    	log.info("NearBy location found");
	    	return new ResponseEntity<>(loc, HttpStatus.OK);
    }
	 
    @GetMapping(value = "/location/open-now")
    @ApiOperation(value = "Api to get open locations now",response = Location.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Location Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error") }) 
    public ResponseEntity<?> searchOpenLocation() { 
	    	List<Location> loc = locService.findOpenLocations(LocalTime.now());
	    	log.info(loc.size() + " number of Open locations found");
	    	return new ResponseEntity<>(loc, HttpStatus.OK);
    }
	 
	
}
