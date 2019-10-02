package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacionbdi.springboot.app.item.models.Producto;


@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/listarProductos")
	public List<Producto> listar();

	@GetMapping("/buscarProducto/{id}")
	public Producto buscarPoridProducto(@PathVariable Long id);
	
	@PostMapping("/guardarProductos")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping("/actulizaProducto/{id}")
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id);
	
	@DeleteMapping("/eliminaProducto/{id}")
	public void eliminar(@PathVariable Long id);

}
