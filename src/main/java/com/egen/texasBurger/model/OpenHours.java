package com.egen.texasBurger.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenHours {
	
    private DayOfWeek day;
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:ss a")
	private LocalTime openTime;
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:ss a")
	private LocalTime closeTime;
    
    
}
