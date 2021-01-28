package com.egen.texasBurger.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.egen.texasBurger.model.User;
import com.egen.texasBurger.repository.RoleRepository;
import com.egen.texasBurger.repository.UserRepository;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/")
@Log4j2
public class AdminRegisterController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister( @Valid @RequestBody User user) {
    	User existingUser = userRepository.findByUserName(user.getUserName());
    	if(existingUser != null ) {
    		log.error("Username already exists");
    		throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    	}
        if(user.getRoles() != null && user.getRoles().size() > 0) {
        	user.getRoles().stream().forEach(role -> roleRepository.save(role));
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}