package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

/**
 * 
 * @author YehanPortilla
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private static final String URL_API_PRODUCTOS = "http://localhost:8001/api/listarProductos";

	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {

		List<Producto> productos = Arrays.asList(clienteRest.getForObject(URL_API_PRODUCTOS, Producto[].class));

		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {

		return null;
	}

}
