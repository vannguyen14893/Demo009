package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;

@Controller
public class MenuController {
	@Autowired
	private MenuService service;

	@GetMapping(value = "/menus")
	public String fillAll(ModelMap map) {
		List<Menu> menus = service.fillAll();
		map.addAttribute("list", menus);
		return "menu";
	}
}
