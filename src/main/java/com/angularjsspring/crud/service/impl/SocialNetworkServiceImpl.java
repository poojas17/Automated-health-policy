package com.angularjsspring.crud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angularjsspring.crud.domain.UserFacebookProfile;
import com.angularjsspring.crud.service.SocialNetworkService;
import com.angularjsspring.crud.utility.FacebookUtil;

@Service("socialNetworkService")
public class SocialNetworkServiceImpl implements SocialNetworkService {
	
	@Autowired
	FacebookUtil facebookUtil;
	
	public UserFacebookProfile getFacebookProfile(String loginId) {
		return facebookUtil.getFacebookUserProfile(loginId);
	}
}
