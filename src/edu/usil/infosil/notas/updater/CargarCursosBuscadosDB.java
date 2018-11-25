package edu.usil.infosil.notas.updater;

import java.time.LocalDateTime;
import java.util.List;

import edu.usil.infosil.notas.NotasFrame;
import edu.usil.infosil.notas.dao.NotasImpl;
import edu.usil.infosil.notas.dto.CursoDTO;

public class CargarCursosBuscadosDB extends Thread {
	private final LocalDateTime inicio;
	private static NotasFrame ventana = null;
	private final String txt;
	private final List<CursoDTO> cursosCoincidentes;
	NotasImpl impl = null;
	public CargarCursosBuscadosDB(String strParaCurso, List<CursoDTO> cursosCoincidentes,LocalDateTime inicio) {
		this.inicio = (LocalDateTime)inicio;
		this.txt = strParaCurso;
		this.cursosCoincidentes = cursosCoincidentes;
	}

	@Override
	public void run() {
		try {
			impl = new NotasImpl();
			cursosCoincidentes.addAll(impl.getSomeCursosCoincidentes(strParaCurso, cursosCoincidentes));
			Collections.sort(cursosCoincidentes, new Comparator<CursoDTO>() {
				@Override
				public int compare(CursoDTO lhs, CursoDTO rhs) {
					return lhs.getId() > rhs.getId() ? 1 : (lhs.getId()<rhs.getId() ? -1 : 0);
				}
			});
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void setVentana(NotasFrame frame) {
		ventana = frame;
	}

	public void Detenerse() {
		if (impl!=null) impl.Detenerse();
	}
}