package com.sena.jdbc.modelo;
// esta clase va representar la tabla de porducto
public class Producto {
	private Integer id;
	private String descripcion;
	private String nombre;
	private Integer cantidad;
	private Integer categoriaId;
	
	public Producto(String nombre,String descripcion,Integer cantidad) {
		
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.cantidad=cantidad;
		//toString(this.nombre,this.descripcion,this.cantidad);
	}

	public Producto(int id, String nombre, String descripcion, int cantidad) {
		 this.id=id;
		 this.nombre=nombre;
		 this.descripcion=descripcion;
		 this.cantidad=cantidad;
	}

	public Producto(int id, String nombre, int cantidad) {
		this.id=id;
		this.nombre=nombre;
		this.cantidad=cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	
	public Integer getId() {
		return id;
	}
	public Integer getCategoriaId() {
		
		return categoriaId;
	}

	public void setId(Integer id) {
		this.id=id;
	}
	
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
		
	}

	
	@Override
	public String toString() {
	
		return String.format(
			"{id:%s,nombre:%s,descripcion:%s,cantidad:%d,categoria:%d}",
			this.id,
			this.nombre,
			this.descripcion,
			this.cantidad,
			this.categoriaId
			);
	}

	

	

	
	
	

	
}
