package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	List<Menu> findByParentId(Integer parentId);
}
