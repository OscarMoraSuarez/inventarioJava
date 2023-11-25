package com.sena.jdbc.controller;

import java.util.List;

import com.sena.jdbc.dao.ProductoDAO;
import com.sena.jdbc.factory.ConnectionFactory;
import com.sena.jdbc.modelo.Categoria;
import com.sena.jdbc.modelo.Producto;


public class ProductoController {

	private ProductoDAO productoDao;
	
	public ProductoController() {
		
		this.productoDao=new ProductoDAO(new ConnectionFactory().recuperaConexion());
		
	}
	
	
	public void modificar(Producto producto){
		
		productoDao.modificar(producto);
		//return new ProductoDAO().modificar(producto);
		
	}

	public int eliminar(Integer id){
	  return productoDao.eliminar(id);
		
	}

	
	public List<Producto> listar(){
		return  productoDao.listar();
	}
	
	public List<Producto> listar(Categoria categoria){
		System.out.println("categoria:"+categoria);
		return productoDao.listar(categoria.getId());
		
	}

	public void guardar(Producto producto,Integer categoriaId){
		
		producto.setCategoriaId(categoriaId);
		productoDao.guardar(producto);
	}
}

	
	


