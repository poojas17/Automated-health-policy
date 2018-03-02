package com.angularjsspring.crud.service;

import com.angularjsspring.crud.domain.User;

public interface UserService {
	void saveUser(User user);
	User findById(Long id);
}
