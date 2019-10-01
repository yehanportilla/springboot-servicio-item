package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	private static final String URL_API_LISTA_PRODUCTOS = "http://servicio-productos/listarProductos";

	private static final String URL_API_BUSCA_POR_ID_PRODUCTO = "http://servicio-productos/buscarProducto/{id}";

	@Autowired
	private RestTemplate clienteRest;

	/**
	 * Method list product of microservicio productos
	 */
	@Override
	public List<Item> findAll() {

		List<Producto> productos = Arrays.asList(clienteRest.getForObject(URL_API_LISTA_PRODUCTOS, Producto[].class));
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	/**
	 * Method search by id product
	 */
	@Override
	public Item findById(Long id, Integer cantidad) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		
		pathVariables.put("id", id.toString());

		Producto producto = clienteRest.getForObject(URL_API_BUSCA_POR_ID_PRODUCTO, Producto.class, pathVariables);

		return new Item(producto, cantidad);
	}

}
