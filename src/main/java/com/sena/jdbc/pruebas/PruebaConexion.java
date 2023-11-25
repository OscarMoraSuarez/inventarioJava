package com.sena.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.sena.jdbc.factory.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        System.out.println("Cerrando la conexión");//Factory encapsulare el codigo de creacion de un objeto en un solo punto

        con.close();
    }

}
