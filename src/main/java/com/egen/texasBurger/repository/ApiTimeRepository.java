package com.egen.texasBurger.repository;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.egen.texasBurger.model.ApiMetadata;

@Repository
public interface ApiTimeRepository extends JpaRepository<ApiMetadata, String> {
	Page<ApiMetadata> findByDate(LocalDate date, Pageable pageable);
	
	Page<ApiMetadata> findByUriAndMethodType(String uri,String method,Pageable paging);
}