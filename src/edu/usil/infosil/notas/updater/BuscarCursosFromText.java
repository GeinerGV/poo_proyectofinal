package edu.usil.infosil.notas.updater;

import java.time.LocalDateTime;

public class BuscarCursosFromText {
	private List<CursosDTO> recientes;
	private LocalDateTime lastTiempoUpdate;
	private final NotasFrame ventana;

	public BuscarCursosFromText(NotasFrame ventana) {
		this.ventana = ventana;
	}

}