
package com.sena.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	
	private Integer id;
	private String  nombre;
	private List<Producto> productos;
	
	public Categoria(int id, String nombre) {
		this.id=id;
		this.nombre=nombre;
		
	}
	
	public void agregarProducto(Producto producto) {
		if (this.productos==null) {
			this.productos= new ArrayList<Producto>();
		}
		this.productos.add(producto);
		
	}

	public Integer getId() {
		
		return this.id;
	}
	
	public List<Producto> getProductos() {
		
		return this.productos;
	}

	
	@Override
	public String toString() {
		return this.nombre;
	}

	
	

	


	
	
}
