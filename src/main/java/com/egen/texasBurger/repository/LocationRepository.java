package com.egen.texasBurger.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.Menu;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
	Page<Location> findAll(Pageable pageable);
	Page<Location> findByNameContaining(String name, Pageable pageable);
	Optional<Location> findByLocationId(String id);
	Page<Location> findByStatus(String status, Pageable pageable);
	Page<Location> findByStatusAndNameContaining(String status,String name, Pageable pageable);
	@Query("{$and :[{'openHours.closeTime' :{ $gt : ?1}} , {'openHours.openTime' :{ $lt : ?1}}, {'openHours.day': ?0 }]}")
	Page<Location> findOpenLocations(DayOfWeek day, LocalTime time, Pageable pageable);
	boolean existsByLocationId(String locationId);
}
