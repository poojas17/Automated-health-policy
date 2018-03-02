package com.angularjsspring.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angularjsspring.crud.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByName(String name);
}
