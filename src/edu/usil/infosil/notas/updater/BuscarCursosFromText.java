package edu.usil.infosil.notas.updater;

import java.time.LocalDateTime;

import javax.swing.JComboBox;

import edu.usil.infosil.notas.NotasFrame;
import edu.usil.infosil.notas.config.InnerManager;
import edu.usil.infosil.xml.read.CursosParser;

public class BuscarCursosFromText {
	private List<CursosDTO> recientes;
	private LocalDateTime lastTiempoUpdate;
	private final JComboBox viewer = null;
	private String lastTxtSearch;
	private Lista<CursosDTO> localCursos;
	private LocalDateTime ultimaPeticionLocalCursos = null;
	private boolean isLocalPeticion = false;

	public BuscarCursosFromText(JComboBox eleBox) {
		this.viewer = eleBox;
		Thread loadThread = new Thread(new Runnable(){
		
			@Override
			public void run() {
				localCursos = getLocalCursos();
			}
		});
	}

	public List<CursosDTO> getLocalCursos() {
		isLocalPeticion = true;
		return CursosParser.readAll(InnerManager.getDownloadCursosPath());
	}

	public void BusquedaSimple(String text) {
		if (localCursos==null) localCursos = getLocalCursos();
		
	}

}