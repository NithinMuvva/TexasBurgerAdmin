package com.egen.texasBurger.service.impl;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.egen.texasBurger.exception.ResourceNotFoundException;
import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.Menu;
import com.egen.texasBurger.repository.LocationRepository;
import com.egen.texasBurger.service.LocationService;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locRepo;

	@Autowired
	MenuServiceImpl menuService;

	public Page<Location> getLocationByStatusAndName(String type, String name, Pageable paging) {
		Page<Location> pagedLocations = locRepo.findByStatusAndNameContaining(type, name, paging);
		return pagedLocations;
	}

	public Location findByLocationId(String id) {
		Optional<Location> optional = locRepo.findByLocationId(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new ResourceNotFoundException("Location by ID: " + id + "doesn't exist");
		}

	}

	public Page<Location> findAllLocation(Pageable paging) {
		Page<Location> location = locRepo.findAll(paging);
		if (!location.isEmpty()) {
			return location;
		} else {
			throw new ResourceNotFoundException( "Locations doesn't exist");
		}
	}

	public void saveLocations(List<Location> locations) {
		try {
			locations.forEach(loc -> saveLocation(loc));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Locations Data is Not Valid", e);
		}

	}

	public void saveLocation(Location location) {

		try {
			locRepo.save(location);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Locations Data is Not Valid", e);
		}
	}

	public Location updateLocation(String id, Location loc) {
		Optional<Location> optional = locRepo.findByLocationId(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Location by ID: " + id + "doesn't exist");
		}
		optional.ifPresent(location -> {
			location.setName(loc.getName());
			location.setAddress(loc.getAddress());
			locRepo.save(location);
		});
		return optional.get();
	}

	public void deleteAllLocation() {
		try {
			locRepo.deleteAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Locations Data is Not Valid", e);
		}

	}

	public void deleteLocationById(String id) {
		Optional<Location> location = locRepo.findById(id);
		if (location.isPresent()) {
			locRepo.delete(location.get());
		} else {
			throw new ResourceNotFoundException("Location by ID: " + id + "doesn't exist");
		}
	}


	public Location findNearByLocation(Double Lat, Double Long)
	{ 

		List<Location> locations = locRepo.findAll();
		Location res = null;
		if(!locations.isEmpty() && locations.size() > 0){
	        Double currLatitude = Lat;
	        Double currLongitude = Long;
	        Double shortestDistance = Double.MAX_VALUE;
	        for(Location l : locations) {
	        	final int R = 6371; // Radius of the earth
	            double latDistance = Math.toRadians(currLatitude - l.getAddress().getLatitude());
	            double lonDistance = Math.toRadians(currLongitude - l.getAddress().getLongitude());
	            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	                    + Math.cos(Math.toRadians(currLatitude)) * Math.cos(Math.toRadians(l.getAddress().getLatitude()))
	                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	            double distance = R * c * 1000; // convert to meters
	            if(distance < shortestDistance) {
	                shortestDistance = distance;
	                res = l;
	            }
	        }
		}
		if(res.equals(null)) {
			throw new ResourceNotFoundException("NearByLocation doesn't exist");
		}
		return res; 	
	}

	public List<Location> findOpenLocations(LocalTime currTime){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
		DayOfWeek day = LocalDate.now().getDayOfWeek();
		if(currTime.equals(null)) {
    		currTime = LocalTime.now();
    	}
		Page<Location> pagedLoc = locRepo.findOpenLocations(day, currTime , null);
		return pagedLoc.getContent();
		
	}

	@Override
	public Page<Location> findByName(String name, Pageable paging) {
		Page<Location> pagedLocations = locRepo.findByNameContaining( name, paging);
		return pagedLocations;
	}

	@Override
	public Page<Location> findByStatus(String status, Pageable paging) {
		Page<Location> pagedLocations = locRepo.findByStatus(status, paging);
		return pagedLocations;
	}

}
