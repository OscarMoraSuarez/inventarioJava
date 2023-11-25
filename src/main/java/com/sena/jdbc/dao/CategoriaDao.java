package com.sena.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sena.jdbc.modelo.Categoria;
import com.sena.jdbc.modelo.Producto;

public class CategoriaDao {
	
	private Connection con;

	public CategoriaDao(Connection con) {
		this.con=con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado= new ArrayList<Categoria>();
		try{
			var querySelect="SELECT ID, NOMBRE FROM CATEGORIA";
			System.out.println(querySelect);
			PreparedStatement statement=con.prepareStatement(
				querySelect);
			try(statement){
				final ResultSet resultSet=statement.executeQuery();
				
				try(resultSet){
					while (resultSet.next()) {
						var categoria=new Categoria(resultSet.getInt("ID"),
								resultSet.getString("NOMBRE"));
						resultado.add(categoria);
						
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);	
		}
		
		return resultado;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado= new ArrayList<Categoria>();
		try{
			var querySelect="SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD " 
					+"FROM CATEGORIA C "
					+"INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID";
			System.out.println(querySelect);
			PreparedStatement statement=con.prepareStatement(
				querySelect);
			try(statement){
				final ResultSet resultSet=statement.executeQuery();
				
				try(resultSet){
					while (resultSet.next()) {
						var categoriaId=resultSet.getInt("C.ID");
						var categoriaNombre	=resultSet.getString("C.NOMBRE");
						var categoria= resultado
								.stream()
								.filter(cat->cat.getId().equals(categoriaId))//busco en la lista un id igual al
								.findAny().orElseGet(()->{//categoriaId y si hay uno lo uso  sino se encuentra orElse
									
								Categoria cat=new Categoria(categoriaId,
											categoriaNombre);
								resultado.add(cat);
								return cat;
								}); //se esxtraen als vairables par ano suarlas en stream() porque cada lambda
						// es otro contexto y si se usa el result set dentro de ese contexto se tiene que poner cada pedazo 
						//de las lambdas dentro de un trycatch se usa la esxtraccion para usar el try wuth resources fuera del while
						Producto  producto = new Producto(resultSet.getInt("P.ID"),
								resultSet.getString("P.NOMBRE"),
								resultSet.getInt("P.CANTIDAD"));
						categoria.agregarProducto(producto);
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);	
		}
		
		return resultado;
	}
	
}
