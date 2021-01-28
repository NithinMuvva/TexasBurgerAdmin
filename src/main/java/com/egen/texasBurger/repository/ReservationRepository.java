package com.egen.texasBurger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.egen.texasBurger.model.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Page<Reservation> findByFullNameContainingAndStatus(String fullName, String status, Pageable paging);
    Optional<Reservation> findByEmail(String email);
    Optional<Reservation> findByDateOfEvent(Date dateOfEvent);
	List<Reservation> findByLocationIdAndDateOfEvent(String id, LocalDate d);
}
