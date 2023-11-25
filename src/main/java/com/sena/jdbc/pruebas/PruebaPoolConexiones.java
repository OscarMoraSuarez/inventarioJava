package com.sena.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.sena.jdbc.factory.ConnectionFactory;

public class PruebaPoolConexiones {

	public static void main(String[] args)throws SQLException{// simulacion de multiples coneziones a la db
		ConnectionFactory connectionFacatory = new ConnectionFactory();

		for (int i=0; i<20;i++) {
			Connection con=connectionFacatory.recuperaConexion();
			System.out.println("Abriendo conexion"+(i+1));
		}
		// si se lelga al tope de conexiones del pool el procimo cliente deberá esperar un momento 
		//hasta que haya una conexion disponible
	}

}
