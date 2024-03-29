package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;

/**
 * 
 * @author YehanPortilla
 *
 */
@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	private static final String URL_API_LISTA_PRODUCTOS = "http://servicio-productos/listarProductos";

	private static final String URL_API_BUSCA_POR_ID_PRODUCTO = "http://servicio-productos/buscarProducto/{id}";

	private static final String URL_API_CREA_PRODUCTO = "http://servicio-productos/guardarProductos";

	private static final String URL_API_ACTUALIZA_PRODUCTO = "http://servicio-productos/actulizaProducto/{id}";
	
	private static final String URL_API_ELIMINA_PRODUCTO = "http://servicio-productos/eliminaProducto/{id}";

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

	/**
	 * Method for create item producto
	 */
	@Override
	public Producto save(Producto producto) {

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		ResponseEntity<Producto> response = clienteRest.exchange(URL_API_CREA_PRODUCTO, HttpMethod.POST, body,
				Producto.class);
		Producto productoResponse = response.getBody();

		return productoResponse;
	}

	/**
	 * Method for update item product
	 */
	@Override
	public Producto update(Producto producto, Long id) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		ResponseEntity<Producto> response = clienteRest.exchange(URL_API_ACTUALIZA_PRODUCTO, HttpMethod.PUT, body,
				Producto.class, pathVariables);

		return response.getBody();
	}
    
	/**
	 * Method delet product
	 */
	@Override
	public void delete(Long id) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		clienteRest.delete(URL_API_ELIMINA_PRODUCTO, pathVariables);
		
	}

}
