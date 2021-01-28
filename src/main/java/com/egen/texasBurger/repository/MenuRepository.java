package com.egen.texasBurger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.egen.texasBurger.model.Menu;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
	Optional<Menu> findByMenuid(String menuid);
	
    Page<Menu> findByMenutype(String menutype,Pageable pageable);
    
    List<Menu> findByLocationId(String locationId);
    
    Page<Menu> findByCategory(String category, Pageable pageable);
    
    @Query(value = "{ 'menuItems.name' :  ?0}")
    
    Page<Menu> findMenuByItemName(String itemName,Pageable pageable);
    
    Page<Menu> findAll(Pageable pageable);
}
