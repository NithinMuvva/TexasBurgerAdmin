package com.egen.texasBurger.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.egen.texasBurger.exception.ResourceNotFoundException;
import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.Menu;
import com.egen.texasBurger.repository.LocationRepository;
import com.egen.texasBurger.repository.MenuRepository;
import com.egen.texasBurger.service.LocationService;
import com.egen.texasBurger.util.StatusType;

@Service
public class MenuServiceImpl {

	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	LocationService locationService;
	
	public Page<Menu> getMenuByCategory(String category,Pageable paging) {
		Page<Menu> pagedMenus = menuRepo.findByCategory(category, paging);
		return pagedMenus;
	}
    public Menu findByMenuId(String id){
        Optional<Menu> optional  = menuRepo.findById(id);
        if(optional.isPresent()){
           return optional.get();
        } else {
            throw new ResourceNotFoundException("Menu by ID: " + id + "doesn't exist");
        }
    }
    
    public List<Menu> findAllMenu(){
        List<Menu> menu  = menuRepo.findAll();
        if(!menu.isEmpty()){
           return menu;
        } else {
            throw new ResourceNotFoundException("Menus doesn't exist");
        }
    }
    
    public void saveMenus(List<Menu> menu) {
    	try {
    		menu.forEach(m->savemenu(m));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu Data is Not Valid", e);
		}
    	
    }
    
    public void savemenu(Menu menu) {
    	
		Location l = locationService.findByLocationId(menu.getLocationId());
		if(l == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot find location with id:"+menu.getLocationId());
		menuRepo.save(menu);
		
    }
    
    public void deletAllMenu() {
    	try {
    		menuRepo.deleteAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Locations Data is Not Valid", e);
		}
    }
    
    public void deleteMenuById(String id){
    	Optional<Menu> menu  = menuRepo.findById(id);
        if(menu.isPresent()){
        	menuRepo.delete(menu.get());
        } else {
            throw new ResourceNotFoundException("Menu by ID: " + id + " doesn't exist");
        }
    }
    
    public Menu updateMenu(String id, Menu menu){
    	
    	Location l = locationService.findByLocationId(menu.getLocationId());
		if(l == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot find location with id:"+menu.getLocationId());
		
        Optional<Menu> optional  = menuRepo.findById(id);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("Menu by ID: " + id + " doesn't exist");
        }
        optional.ifPresent(m -> {
        	m.setCategory(menu.getCategory());
        	m.setMenuItems(menu.getMenuItems());
        	m.setMenutype(menu.getMenutype());
        	m.setLocationId(menu.getLocationId());
            menuRepo.save(m);});
        return optional.get();
    }
    
    public Page<Menu> findByMenuType(String type){
        Page<Menu> menu = menuRepo.findByMenutype(type, null);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu by Type: " + type + "doesn't exist");
        } else {
            return menu;
        }
    }
    
	
 
	public Page<Menu> findByMenuItem(String itemName, Pageable paging)   {
    	 if(StringUtils.isEmpty(itemName)) return menuRepo.findAll(paging);
    	Page<Menu> menu = menuRepo.findMenuByItemName(itemName, paging);
		return menu; 
    	
    }
	
	public Page<Menu> findByCategory(String category, Pageable paging) {
		return menuRepo.findByCategory(category, paging);
	}
	
	public List<Menu> findByLocationId(String locId) {
		return menuRepo.findByLocationId(locId);
	}
}
