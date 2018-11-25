package edu.usil.infosil.notas.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class NotasImpl {
	private Connection conn = null;
	private String url = "jdbc:mysql://127.0.0.1:3306/cursosdb";

	public NotasImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("No se pudo cargar el driver");
		}
	}

	private getConnection() {
		try {
			conn = DriverManager.getConnection(url, "root", "");
		} catch (Exception e) {
			System.out.println("No se pudo obtener conexion");
		}
	}

}