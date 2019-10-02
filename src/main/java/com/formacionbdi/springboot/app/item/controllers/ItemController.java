package com.formacionbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

/**
 * 
 * @author YehanPortilla
 *
 */
@RefreshScope
@RestController
public class ItemController {

	private static Logger log = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	/**
	 * Inyectamos configuracion de properties de la carpeta config del archivo
	 * servicio-items-properties
	 */
	@Value("${configuracion.texto}")
	private String texto;

	@GetMapping("/listaItems")
	public List<Item> listar() {
		return itemService.findAll();

	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo") // Camino alternativo
	@GetMapping("/detalle/{id}/cantidad/{cantidad}")
	public Item buscarPorId(@PathVariable Long id, @PathVariable Integer cantidad) {

		return itemService.findById(id, cantidad);
	}

	/**
	 * Method alternative for error
	 * 
	 * @param id
	 * @param cantidad
	 * @return
	 */
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();

		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);

		return item;
	}

	/**
	 * Metodo para mostrar como respuesta la configuracion de servidor en un handler
	 * 
	 * @param puerto
	 * @return ResponseEntity
	 */
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {

		log.info(texto);

		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {

			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}

		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

	/**
	 * Metodo para crear el producto desde servicio items
	 * @param producto
	 * @return
	 */
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {

		return itemService.save(producto);
	}
	
	
	/**
	 * Metodo encargado de actualizar el producto des de servicio items
	 * @return
	 */
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar (@RequestBody Producto producto,@PathVariable Long id) {

		return itemService.update(producto, id);
	} 
	
	/**
	 * Metodo encargado de eliminar el producto des de el servicio items
	 * @param id
	 */
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		
		itemService.delete(id);
	}

}
