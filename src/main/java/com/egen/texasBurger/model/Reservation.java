package com.egen.texasBurger.model;

import java.time.LocalDate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection = "reservations")
public class Reservation {
	@Id
	private String id;
	@NotEmpty
	private String fullName;
	@NotEmpty
	private String contact;
	private String email;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dateOfBooking;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dateOfEvent;
	@NotEmpty
	private String eventCategory;
	private String eventPackageSelected;
	private ReservationSlot resSlot;
	private String status;
	@NotEmpty
	private String locationId;
}
