package edu.usil.infosil.notas.dto;

import java.sql.Timestamp;

public class CursoDTO {
	private int id;
	private String code;
	private int creditos;
	private String nombre;
	private Timestamp fechaMod;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Timestamp getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(Timestamp fechaMod) {
		this.fechaMod = fechaMod;
	}
	@Override
	public String toString() {
		return "Curso<"+nombre+">[id="+id+", code="+code+", creditos="+creditos+"]";
	}
}