package com.angularjsspring.crud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.angularjsspring.crud.domain.User;
import com.angularjsspring.crud.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 
    @Autowired
    UserService userService;
    
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user ,UriComponentsBuilder uriComponentsBuilder) {
    	logger.info("Saving User: " + user);
    	
    	userService.saveUser(user);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
}
