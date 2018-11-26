package edu.usil.infosil.notas.updater;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import edu.usil.infosil.notas.NotasFrame;
import edu.usil.infosil.notas.config.InnerManager;
import edu.usil.infosil.notas.dto.CursoDTO;
import edu.usil.infosil.xml.read.CursosParser;

public class BuscarCursosFromText {

	private boolean modoAsincrono = false;
	private CursosParser cursosParser;
	private List<CursoDTO[]> cursosCoincidentes;
	private String txtSearchNow;

	private List<CursosDTO> recientes;
	private LocalDateTime lastTiempoUpdate;
	private final JComboBox<String> viewer = null;
	private Lista<CursosDTO> localCursos = null;
	private LocalDateTime ultimaPeticionLocalCursos;
	private boolean isLocalPeticion = false;

	private Runnable runnableGetLocalCursos = new Runnable() {
		@Override
		public void run() {
			localCursos = getLocalCursos();
		}
	};

	public BuscarCursosFromText() {
		cursosParser = new CursosParser(new String [] {InnerManager.getDownloadCursosPath()});
		Thread loadThread = new Thread(runnableGetLocalCursos);
	}

	public List<CursosDTO> getLocalCursos() {
		// isLocalPeticion = true;
		return cursosParser.readAll(0);
	}

	public void BusquedaSimple(String text) {
		txtSearchNow = text;
		if (localCursos==null) {
			if (!modoAsincrono) {
				localCursos = getLocalCursos();
			} else {
				localCursos = new ArrayList<CursoDTO>();
			}
		}
		setCoincidentes();
	}

	public void showCursosInViewer(JComboBox<String> viewBox) {
		/* Collections.sort(cursosCoincidentes, new Comparator <CursoDTO>() {
			@Override
			public int compare(CursoDTO lhs, CursoDTO rhs) {
				return lhs.getId() > rhs.getId() ? 1 : (lhs.getId()<rhs.getId() ? -1 : 0);
			}
		}); */
		// String[] itmsToShow = new String[cursosCoincidentes.get(0).length + cursosCoincidentes.get(1).length];
		List<String> itmsToShowList = new ArrayList<String>();
		int itmsLen = 0;
		for (CursoDTO[] arrCursos : cursosCoincidentes) {
			String[] itmsPartial = new String[arrCursos.length];
			for (int j = 0; j < arrCursos.length; j++) {
				CursoDTO cDto = arrCursos[j];
				itmsPartial[j] = cDto.getNombre() + " - " + cDto.getCode();
				++itmsLen;
			}
			Arrays.sort(itmsPartial);
			itmsToShowList.addAll(Arrays.asList(itmsPartial));
		}
		String txtInJCB = viewBox.getSelectedItem();
		viewBox.setModel(new DefaultComboBoxModel<>(itmsToShowList.toArray(new String[itmsToShowList.size()])));
		viewBox.setSelectedItem(txtInJCB);
		if (jCBsearcher.getItemCount()>0) viewBox.showPopup();
	}

	public void setCoincidentes() {
		cursosCoincidentes = getCoincidentes(txtSearchNow, localCursos);
	}

	public void setViewer(JComboBox<String> viewBox) {
		viewer = viewBox;
	}

	public static List<CursoDTO[]> getCoincidentes(String text, List<CursosDTO> allCursos) {
		List<CursoDTO> cCoincidentes1 = new ArrayList<CursoDTO>();
		List<CursoDTO> cCoincidentes2 = new ArrayList<CursoDTO>();
		for (CursoDTO curso : allCursos) {
			if ((curso.getNombre()).toLowerCase().startsWith(strParaCurso.toLowerCase())) {
				cCoincidentes1.add(curso);
			}
			if (strParaCurso.length()>=5) {
				if ((curso.getNombre()).toLowerCase().substring(1).contains(strParaCurso.toLowerCase())) {
					cCoincidentes2.add(curso);
				}
			}
		}
		List<CursoDTO[]> cCoincidenList = new ArrayList<CursoDTO[]>(2);
		cCoincidenList.add(0, cCoincidentes1.toArray(new CursoDTO[cCoincidentes1.size()]));
		cCoincidenList.add(1, cCoincidentes2.toArray(new CursoDTO[cCoincidentes2.size()]));
		return cCoincidenList;
		/* return new CursoDTO[][] {
			cCoincidentes1.toArray(new CursoDTO[cCoincidentes1.size()]),
			cCoincidentes2.toArray(new CursoDTO[cCoincidentes2.size()])
		}; */
	}

}