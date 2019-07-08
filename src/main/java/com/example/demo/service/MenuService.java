package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Menu;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.RoleRepository;

@Service
@Transactional
public class MenuService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;

	public List<Menu> fillAll() {
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> menuFather = menuRepository.findByParentId(0);
		for (Menu menu : menuFather) {
			List<Menu> menuChild = menuRepository.findByParentId(menu.getId());
			menus.add(menu);
			menus.addAll(menuChild);
		}
		return menus;
	}
}
