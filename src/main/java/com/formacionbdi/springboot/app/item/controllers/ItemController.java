package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

/**
 * 
 * @author YehanPortilla
 *
 */
@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	@GetMapping("/listaItems")
	public List<Item> listar() {
		return itemService.findAll();

	}

	@GetMapping("/detalle/{id}/cantidad/{cantidad}")
	public Item buscarPorId(@PathVariable Long id, @PathVariable Integer cantidad) {

		return itemService.findById(id, cantidad);
	}

}
