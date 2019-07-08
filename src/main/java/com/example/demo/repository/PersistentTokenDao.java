package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PersistentLogins;

public interface PersistentTokenDao extends JpaRepository<PersistentLogins, String> {
	PersistentLogins findBySeries(String series);

	PersistentLogins findByUsername(String username);
}
