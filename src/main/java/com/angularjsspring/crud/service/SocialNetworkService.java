package com.angularjsspring.crud.service;

import com.angularjsspring.crud.domain.UserFacebookProfile;

public interface SocialNetworkService {
	UserFacebookProfile getFacebookProfile(String loginId);
}
