package com.egen.texasBurger.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.egen.texasBurger.model.Location;

public interface LocationService {

	public void saveLocation(Location location);
	public void saveLocations(List<Location> location);
	public Location findByLocationId(String id);
	public Page<Location> findAllLocation(Pageable paging);
	public Location updateLocation(String id, Location location);
	public void deleteLocationById(String id);
	public void deleteAllLocation();
	public Page<Location> getLocationByStatusAndName(String type,String name, Pageable paging);
	public Location findNearByLocation(Double latitude, Double longitude);
	public List<Location> findOpenLocations(LocalTime currTime);
	public Page<Location> findByName(String name,Pageable paging);
	public Page<Location> findByStatus(String status,Pageable paging);
}
