package com.sena.jdbc.controller;

import java.util.List;

import com.sena.jdbc.dao.CategoriaDao;
import com.sena.jdbc.factory.ConnectionFactory;
import com.sena.jdbc.modelo.Categoria;
	
	

public class CategoriaController {
	private CategoriaDao categoriaDao;
	
	
	public CategoriaController() {
		var factory= new ConnectionFactory();
		this.categoriaDao=new CategoriaDao(factory.recuperaConexion());
		
	}
	
	

	public List<Categoria> listar() {
		
		return categoriaDao.listar();
	}

    public List<Categoria> cargaReporte() {
        
        //return this.listar();
    	return this.categoriaDao.listarConProductos();
    }

}
