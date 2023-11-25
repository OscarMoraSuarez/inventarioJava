package com.sena.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sena.jdbc.modelo.Producto;

public class ProductoDAO {// Clase DAO data access object

	final private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
		//this.con= new ConnectionFactory().recuperaConexion()
	}

	
	public void guardar(Producto producto) {
		System.out.println(producto);
		var cantidad = producto.getCantidad();
		Integer maximaCantidad = 50;
		try {
			// no se hace el commit de la transacción
			con.setAutoCommit(false);// cuando se le pasa un numero como 60 con los platos
			// se abre una nueva transacción para los primeros 50 y otra para el resto
			// guarda los datos pero como el resto es menor
			// que cincuenta se lanza el error y se pierde la informacion para cada
			// ejecucion del insert se crea una transaccion devuelve
			// el resultSet y cierra la transaccion pasara lo mismo en el sgundo intento se
			// abre la transaccion se insertan los datos y se cierra
			// entonces se tona la responsabilidad del jdbc para hacerla nosotros y
			// garantizar que se guarde todo o no se guarde nada
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO (nombre,descripcion,cantidad,categoria_id)" + "VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {

				do {
					int cantidadParaGuardar = Math.min(cantidad, maximaCantidad);
					ejecutaRegistro(producto, statement, cantidadParaGuardar);
					cantidad -= maximaCantidad;
				} while (cantidad > 0);
				con.commit();// como le quitamso responsabilidad a jdbc ahora hacemos el commit para
			} // garantizar la ejecucion de los
			System.out.println("commit"); // comandos del loop
		} catch (SQLException e) {
			System.out.println("Rollback");

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);

			} // si la ejecucion tiene un error echamos la transacción para atrás
			throw new RuntimeException(e);
		}
	}

	private void ejecutaRegistro(Producto producto, PreparedStatement statement, Integer cantidadParaGuardar)
			throws SQLException {
		/*
		 * if (cantidad < 50) { throw new RuntimeException("Ocurrio un error"); }
		 */
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		// statement.setInt(3, producto.getCantidad());
		statement.setInt(3, cantidadParaGuardar);
		statement.setInt(4,producto.getCategoriaId());
		statement.execute();
		final ResultSet resultset = statement.getGeneratedKeys();
		try (resultset) {// resultset implementa autoclosable jvm cierra esos recursos
			while (resultset.next()) {
				producto.setId(resultset.getInt(1));
				// System.out.println(String.format("Fue insertado el producto de ID: %d",
				// resultset.getInt(1)));
				System.out.println(String.format("Fue insertado el producto de ID: %s", producto));
			}
		}

	}

	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						Producto producto = new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD"));

						resultado.add(producto);

					}
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return resultado;

	}
	
	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();
		
		try {
			var querySelect="SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD "
	                + "FROM PRODUCTO "
	                + "WHERE CATEGORIA_ID = ?";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con
					.prepareStatement(querySelect);
			try (statement) {
				statement.setInt(1,categoriaId);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						Producto producto = new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD"));

						resultado.add(producto);

					}
				}
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
	public void modificar(Producto producto) {
		
		try{

			final PreparedStatement statement = con
					.prepareStatement("UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = ?");
			try (statement) {
				// Asignar valores a los parámetros de la sentencia preparada
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());

				// Ejecutar la actualización
				int filasAfectadas = statement.executeUpdate();

				if (filasAfectadas > 0) {
					System.out.println("La actualización fue exitosa.");
					System.out.println(producto);
				} else {
					System.out.println("La actualización no afectó ninguna fila.");
					
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int eliminar(Integer id) {
		try{
			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID=?");
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	
	
	

}
