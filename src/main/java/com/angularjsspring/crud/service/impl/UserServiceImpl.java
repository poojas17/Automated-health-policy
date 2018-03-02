package com.angularjsspring.crud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angularjsspring.crud.dao.UserRepository;
import com.angularjsspring.crud.domain.User;
import com.angularjsspring.crud.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		if(id!=null)
			return userRepository.findOne(id);
		return null;
	}

}
