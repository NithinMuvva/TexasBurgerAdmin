package com.egen.texasBurger.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.egen.texasBurger.exception.ResourceNotFoundException;
import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.Reservation;
import com.egen.texasBurger.repository.ReservationRepository;
import com.egen.texasBurger.service.LocationService;

@Service
public class ReservationServiceImpl {

	@Autowired
    ReservationRepository reservationRepository;
	
	@Autowired
	LocationService locationService;
	
	public Page<Reservation> findByFullNameAndStatus(String fullName, String status, Pageable paging){
        Page<Reservation> reservation = reservationRepository.findByFullNameContainingAndStatus(fullName,status,paging);
        return reservation;
    }
	
	public Reservation findByEmail(String email){
        Optional<Reservation> reservation = reservationRepository.findByEmail(email);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with Email : " + email + " not found");
        }
    }
	
	public Reservation findById(String id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with ID : " + id + " not found");
        }
    }
	
	public Reservation findByDate(Date date){
        Optional<Reservation> reservation = reservationRepository.findByDateOfEvent(date);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with Date : " + date.toString() + " not found");
        }
    }
	
	public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
	
	public void saveReservation(Reservation reservation){
		Location l = locationService.findByLocationId(reservation.getLocationId());
		if(l == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot find location with id:"+reservation.getLocationId());
        if (reservation.getDateOfEvent().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter valid event date");
        }
        Map<String, Integer> slotMap = getOpenReservations(reservation.getLocationId(),LocalDate.now());
        if(slotMap.get(reservation.getResSlot().getType()) < reservation.getResSlot().getNumberOfSlots()) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry!!! we have only"+slotMap.get(reservation.getResSlot().getType())+ " slots left");
        }
        reservationRepository.save(reservation);
    }
	public void saveReservations(List<Reservation> reservations){
        reservations.forEach(
        		reservation -> saveReservation(reservation)
        );
    }
	
	public void updateReservation(String id, Reservation reservation){
		
		Location l = locationService.findByLocationId(reservation.getLocationId());
		if(l == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot find location with id:"+reservation.getLocationId());
		
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Reservation with ID : " + id + " not found");
        }
        LocalDate today = LocalDate.now();
        if (reservation.getDateOfEvent().isBefore(today)) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter valid event date");
        }
        Map<String, Integer> slotMap = getOpenReservations(reservation.getLocationId(),reservationOptional.get().getDateOfEvent());
        if(slotMap.get(reservation.getResSlot().getType()) - reservationOptional.get().getResSlot().getNumberOfSlots() < reservation.getResSlot().getNumberOfSlots()) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry!!! we have only"+slotMap.get(reservation.getResSlot().getType())+ " slots left");
        }
        reservationOptional.ifPresent(reservations -> {
            reservations.setContact(reservation.getContact());
            reservations.setDateOfBooking(reservation.getDateOfBooking());
            reservations.setFullName(reservation.getFullName());
            reservations.setDateOfEvent(reservation.getDateOfEvent());
            reservations.setEventCategory(reservation.getEventCategory());
            reservations.setResSlot(reservation.getResSlot());
            reservationRepository.save(reservations);
        });
    }
	
	 public void deleteReservation(String id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Reservation by ID: " + id + " doesn't exist");
        }
    }
	 
	public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }
	
	public Map<String, Integer> getOpenReservations(String locId,LocalDate date) {
		List<Reservation> res = reservationRepository.findByLocationIdAndDateOfEvent(locId,date);
		Integer Morningsum = res.stream().filter(o -> o.getResSlot().getType().equals( "MORNING")).mapToInt(o -> o.getResSlot().getNumberOfSlots()).sum();
		Integer Afternoonsum = res.stream().filter(o -> o.getResSlot().getType() == "AFTERNOON").mapToInt(o -> o.getResSlot().getNumberOfSlots()).sum();
		Integer Evngsum = res.stream().filter(o -> o.getResSlot().getType() == "EVENING").mapToInt(o -> o.getResSlot().getNumberOfSlots()).sum();
		Map<String, Integer> slotMap = new HashMap<>();
		slotMap.put("MORNING", 10 - Morningsum);
		slotMap.put("AFTERNOON", 10 - Afternoonsum);
		slotMap.put("EVENING", 10  -Evngsum);
		return slotMap;
	}
	

}
