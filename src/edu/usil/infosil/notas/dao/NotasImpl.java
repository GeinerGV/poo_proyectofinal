package edu.usil.infosil.notas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.usil.infosil.notas.dto.CursoDTO;

public class NotasImpl {
	private Connection conn = null;
	private String url = "jdbc:mysql://127.0.0.1:3306/infosildb";

	public NotasImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("No se pudo cargar el driver");
		}
	}

	private Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url, "root", "8/rExw|e~?X7\\OD;!ecK's.eS2E@FhHOAFIvyOMp");
		} catch (Exception e) {
			System.out.println("No se pudo obtener conexion");
		}
		return conn;
	}

	public List<CursoDTO> getSomeCursosCoincidentes(String strContent, List<CursoDTO> listCursosEvitar) {
		PreparedStatement stmt = null;
		ResultSet rs;
		String sql = "SELECT *  FROM tbcursos WHERE nombre LIKE CONCAT(" + (strContent.length()>=3 ? "'%'," :"") + "?,'%')";
		for (CursoDTO cursoCoincidente: listCursosEvitar) {
			sql += " and id!="+cursoCoincidente.getId();
		}
		sql += " LIMIT 5;";
		List<CursoDTO> cursos = new ArrayList<CursoDTO>();
		try {
			this.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, strContent);
			rs = stmt.executeQuery();
			// System.out.println(rs.getStatement());
			while(rs.next()) {
				CursoDTO curso = new CursoDTO();
				curso.setId( rs.getInt("ID") );
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setCode(rs.getString("CODE"));
				curso.setCreditos(rs.getInt("CREDITOS"));
				curso.setFechaMod(rs.getTimestamp("FECHAMOD"));
				cursos.add(curso);
				// System.out.println(curso);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* String[] listNames = new String[nombres.size()];
		for (int i = 0; i < nombres.size(); i++) {
			listNames[i] = nombres.get(i);
		} */
		// return nombres.toArray(new String[nombres.size()]);
		return cursos;
	}
}