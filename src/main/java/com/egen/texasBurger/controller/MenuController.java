package com.egen.texasBurger.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.texasBurger.model.Location;
import com.egen.texasBurger.model.Menu;
import com.egen.texasBurger.model.PaginationResponse;
import com.egen.texasBurger.service.impl.MenuServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@Api(value="Menu", description  ="API Operations related to Menu of Restuarants ")
public class MenuController {
	
	@Autowired
	MenuServiceImpl menuService;
	
	@GetMapping(value = "/menus")
	@ApiOperation(value = "Filter menu by itemName", response = Location.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  filterMenuByItemName(@RequestParam(required = false) String itemName,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size) {
		
        	 Pageable paging = PageRequest.of(page, size);
        	 Page<Menu> pagedLocations = menuService.findByMenuItem(itemName,paging);
        	 PaginationResponse pr = new PaginationResponse(pagedLocations.getNumber(),
     				pagedLocations.getTotalElements(),
     				pagedLocations.getTotalPages(),
     				pagedLocations.getContent());
            return new ResponseEntity<>(pr, HttpStatus.OK);
    }
	
	@GetMapping(value = "/menus/byCategory/{categoryName}")
	@ApiOperation(value = "Get paginated menus by Category ", response = Location.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  filterMenuByCategory(@PathVariable("categoryName") String category,
		      @RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size) {
        	 Pageable paging = PageRequest.of(page, size);
        	 Page<Menu> pagedLocations = menuService.findByCategory(category,paging);
        	 PaginationResponse pr = new PaginationResponse(pagedLocations.getNumber(),
     				pagedLocations.getTotalElements(),
     				pagedLocations.getTotalPages(),
     				pagedLocations.getContent());
            return new ResponseEntity<>(pr, HttpStatus.OK);
    }
	
	@GetMapping(value = "/menus/bulk")
	@ApiOperation(value = "Get all the Menu", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  findAllMenu() {
            List<Menu> menu = menuService.findAllMenu();
            return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PostMapping(value = "/menus/bulk")
    @ApiOperation(value = "Post all the Menu", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 404, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> saveMenus(@Valid @RequestBody List<Menu> menu) {
        	menuService.saveMenus(menu);
            return new ResponseEntity<>("Menu is created sucessfully", HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/menus/bulk")
    @ApiOperation(value = "Api to delete all the Menu", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllMenu() {
        	menuService.deletAllMenu();
            return new ResponseEntity<>("Deleted sucessfully", HttpStatus.OK);
    }
    
    @GetMapping(value = "/menus/{id}")
    @ApiOperation(value = "Api to get menu by id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  getMenuById(@PathVariable("id") String id) {
        	Menu menu = menuService.findByMenuId(id);
            return new ResponseEntity<>(menu, HttpStatus.OK);
    }
	
    @PostMapping(value = "/menus")
    @ApiOperation(value = "Api to post menu", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createMenu(@Valid @RequestBody Menu menu) {
            menuService.savemenu(menu);
            return new ResponseEntity<>("Menu is Saved", HttpStatus.OK);
    }
    
    @PutMapping(value = "/menus/{menuId}")
    @ApiOperation(value = "Api update menu by id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  updateLocationById(@PathVariable("menuId") String id, @Valid @RequestBody Menu menu) {
        	Menu updateMenu = menuService.updateMenu(id, menu);
			return new ResponseEntity<>(updateMenu, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/menus/{id}")
    @ApiOperation(value = "Api to delete menu by id", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  updateLocationById(@PathVariable("id") String id) {
        try {
        	menuService.deleteMenuById(id);
			return new ResponseEntity<>("Menu is deleted sucessfully", HttpStatus.OK);
            
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/menus/menuType/{type}")
    @ApiOperation(value = "Api to get menu by menu type", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByMenuType(@PathVariable("type") String type){
        try{
            return new ResponseEntity<>(menuService.findByMenuType(type), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/menus/location/{locationId}")
    @ApiOperation(value = "Api to get menu by Location ID", response = Menu.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Menu Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  getMenuByLocationId(@PathVariable("locationId") String locId) {
        	List<Menu> menu = menuService.findByLocationId(locId);
            return new ResponseEntity<>(menu, HttpStatus.OK);
    }

}
