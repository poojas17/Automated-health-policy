package com.angularjsspring.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angularjsspring.crud.domain.UserFacebookProfile;
import com.angularjsspring.crud.service.SocialNetworkService;

@RestController
@RequestMapping("/social")
public class UserSocialNetworkController {
	
	@Autowired
	SocialNetworkService socialNetworkService;
	
	@RequestMapping(value="/fbprofile/{loginId}" , method = RequestMethod.GET)
	public ResponseEntity<?> getFacebookProfile(@PathVariable("loginId") String loginId) {
		UserFacebookProfile userProfile = socialNetworkService.getFacebookProfile(loginId);
		return new ResponseEntity<UserFacebookProfile>(userProfile, HttpStatus.OK);
	}
}
