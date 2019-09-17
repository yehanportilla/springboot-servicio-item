package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formacionbdi.springboot.app.item.models.Producto;

@RequestMapping("/api")
@FeignClient(name = "servicio-productos", url = "localhost:8001")
public interface ProductoClienteRest {

	@GetMapping("/listarProductos")
	public List<Producto> listar();

	@GetMapping("/buscarProducto/{id}")
	public Producto buscarPoridProducto(@PathVariable Long id);

}
