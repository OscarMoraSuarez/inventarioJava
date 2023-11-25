package com.sena.jdbc.factory;

import java.sql.Connection;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	private DataSource dataSource;
	public ConnectionFactory() {// uso el constructor de la clase
		var pooledDataSource=new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/inventariosena?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("hetchelzican");
		pooledDataSource.setMaxPoolSize(10);// el maximo de conexiones abiertas
		
		this.dataSource=pooledDataSource;
		
		
	}
	public Connection recuperaConexion(){
		/*return  DriverManager.getConnection(
				"jdbc:mysql://localhost/controlstock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"");*/
		
		try {
			return this.dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
