package edu.usil.infosil.notas;

// import com.placeholder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;

import edu.usil.infosil.notas.dao.NotasImpl;
import edu.usil.infosil.notas.dto.CursoDTO;
import edu.usil.infosil.notas.updater.CargarCursosBuscadosDB;
import edu.usil.infosil.xml.read.CursosParser;

public class NotasFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private int btnSel;
	private final Color[] colores = {Color.gray, Color.orange};
	
	public NotasFrame() {
		btnSel = -1;
		setTitle("Administrador de Notas");
		initComponents();
	}

	public int getBtnSel() {
		return btnSel;
	}

	private void selectBtn(int num) {
		if (num != btnSel) {
			if (btnSel==-1) {
				for (int i = 0; i < listTopBarBtns.length; i++) {if (i!=num) listTopBarBtns[i].setBackground(colores[0]);}
			} else if (btnSel>=0) listTopBarBtns[btnSel].setBackground(colores[0]);
			listTopBarBtns[num].setBackground(colores[1]);
			btnSel = num;
		}
	}

	private void EstablecerCompentesPnlBtnSel() {
		GroupLayout gLayoutPnlBtnSel = new GroupLayout(jPnlBtnSel);
		listElesPnlBtnSel = new ArrayList<>();
		if (btnSel == 0) {
			jPnlBtnSel.setLayout(gLayoutPnlBtnSel);
			JComboBox<String> jCBsearcher = new JComboBox<String>();
			jCBsearcher.setEditable(true);
			jCBsearcher.setModel(new DefaultComboBoxModel<>(new String[] {}));
			jCBsearcher.setSelectedItem("");
			JButton jBtnSearch = new JButton("Buscar");

			JLabel jLblPerido = new JLabel("Periodo Académico:");
			JTextField jTxtfPerido = new JTextField();
			// Class.forName("com.placeholder.PlaceHolder");
			// placeholder.PlaceHolder holder = new placeholder.PlaceHolder(jTxtfPerido, "2018-02, 2018-2, 18-2, ...");
			// Class.forName("com.placeholder.PlaceHolder").getClass().(jTxtfPerido, "f");// (jTxtfPerido, "2018-02, 2018-2, 18-2, ...");
			listElesPnlBtnSel.add(0, jCBsearcher);
			listElesPnlBtnSel.add(1, jBtnSearch);
			listElesPnlBtnSel.add(2,jLblPerido);
			listElesPnlBtnSel.add(3,jTxtfPerido);
			
			// TODO: Creando el primer hilo de recogida de datos y estableciendo la ventana
			CargarCursosBuscadosDB.setVentana(this);
			// CargarCursosBuscadosDB cargarCursosDB = new CargarCursosBuscadosDB("", new ArrayList<CursoDTO>(), LocalDateTime.now());
			CargarCursosBuscadosDB cargarCursosDB = null;

			jBtnSearch.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cargarCursosDB!=null) cargarCursosDB.Detenerse();
					buscarCursosActionPerformed(e, cargarCursosDB);
				}
			});

			gLayoutPnlBtnSel.setHorizontalGroup(
				gLayoutPnlBtnSel.createParallelGroup(Alignment.CENTER)
				.addGroup(gLayoutPnlBtnSel.createSequentialGroup()
					.addGap(20)
					.addComponent(jCBsearcher, (30+175*3+30), GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(jBtnSearch)
					.addGap(20)
				)
				.addGroup(gLayoutPnlBtnSel.createSequentialGroup()
					.addComponent(jLblPerido)
					.addComponent(jTxtfPerido, 300, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				)
			);
			gLayoutPnlBtnSel.setVerticalGroup(
				gLayoutPnlBtnSel.createSequentialGroup()
				.addGroup(gLayoutPnlBtnSel.createParallelGroup()
					.addGap(10)
					.addComponent(jCBsearcher, 35, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(jBtnSearch, 35, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
				)
				.addGroup(gLayoutPnlBtnSel.createParallelGroup(Alignment.CENTER)
					.addComponent(jLblPerido)
					.addComponent(jTxtfPerido)
				)
			);
			// listElesPnlBtnSel.add(new JComboBox<>());
			// listElesPnlBtnSel.get(0).
		}
	}

	private void buscarCursosActionPerformed(ActionEvent evt, CargarCursosBuscadosDB cargadorDeDatosDB) {
		JComboBox<String> jCBsearcher = (JComboBox<String>)listElesPnlBtnSel.get(0);
		String strParaCurso = ((String)jCBsearcher.getSelectedItem()).trim();
		int selCurso = jCBsearcher.getSelectedIndex();

		// listElesPnlBtnSel.add(LocalDateTime.now());
		if (selCurso==-1 && strParaCurso.length()>0) {
			List<CursoDTO> listaCursosAlmacenados = CursosParser.readAll("src/test/recursos/xml/Cursos.xml");
			List<CursoDTO> cursosCoincidentes = new ArrayList<CursoDTO>();
			List<String> concidencias = new ArrayList<String>();
			
			cargadorDeDatosDB = new CargarCursosBuscadosDB(strParaCurso, cursosCoincidentes, LocalDateTime.now(), this);

			for (CursoDTO curso : listaCursosAlmacenados) {
				if (strParaCurso.length()>=3) {
					if ((curso.getNombre()).toLowerCase().contains(strParaCurso.toLowerCase())) {
						cursosCoincidentes.add(curso);
					}
				} else if ((curso.getNombre()).toLowerCase().startsWith(strParaCurso.toLowerCase())) {
					cursosCoincidentes.add(curso);
				}
			}

			/* NotasImpl impl = new NotasImpl();
			cursosCoincidentes.addAll(impl.getSomeCursosCoincidentes(strParaCurso, cursosCoincidentes));
			Collections.sort(cursosCoincidentes, new Comparator<CursoDTO>() {
				@Override
				public int compare(CursoDTO lhs, CursoDTO rhs) {
					return lhs.getId() > rhs.getId() ? 1 : (lhs.getId()<rhs.getId() ? -1 : 0);
				}
			}); */
			Collections.sort(cursosCoincidentes, new Comparator <CursoDTO>() {
				@Override
				public int compare(CursoDTO lhs, CursoDTO rhs) {
					return lhs.getId() > rhs.getId() ? 1 : (lhs.getId()<rhs.getId() ? -1 : 0);
				}
			});
			for (CursoDTO curso : cursosCoincidentes) {
				concidencias.add(curso.getNombre() + " - " + curso.getCode());
			}

			jCBsearcher.setModel(new DefaultComboBoxModel<String>(concidencias.toArray(new String[concidencias.size()])));
			jCBsearcher.setSelectedItem(strParaCurso);
			if (jCBsearcher.getItemCount()>0) jCBsearcher.showPopup();
			// cargardorDeDatos.start();
		}
	}

	private void actualizarItemsjCBCursosSearch(List<String> listaCursos, LocalDateTime tiempo) {
		
	}

	private void initComponents() {
		jPnlTopBar = new JPanel();
		listTopBarBtns = new JButton[3];

		listTopBarBtns[0] = new JButton("Seleccionar un curso");
		listTopBarBtns[1] = new JButton("Crear un curso");
		listTopBarBtns[2] = new JButton("Mis cursos");
		selectBtn(0);

		jPnlBtnSel = new JPanel();
		EstablecerCompentesPnlBtnSel();
		// jCBCiclosUser = new JComboBox<>();
		// jTblListaCursosCiclo = new JTable(new String[] {null},new String[] {"Cursos"});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GroupLayout gLayoutTopBar = new GroupLayout(jPnlTopBar);
		jPnlTopBar.setLayout(gLayoutTopBar);
		gLayoutTopBar.setHorizontalGroup(
			gLayoutTopBar.createParallelGroup(Alignment.CENTER)
			.addGroup(gLayoutTopBar.createParallelGroup(Alignment.CENTER)
				.addGap(15)
				.addGroup(gLayoutTopBar.createSequentialGroup()
					.addContainerGap()
					.addComponent(listTopBarBtns[0], 175, GroupLayout.DEFAULT_SIZE, 300)
					.addComponent(listTopBarBtns[1], 175, GroupLayout.DEFAULT_SIZE, 300)
					.addComponent(listTopBarBtns[2], 175, GroupLayout.DEFAULT_SIZE, 300)
					.addContainerGap()
				)
				.addGap(15)
			)
			.addComponent(jPnlBtnSel)
		);
		
		gLayoutTopBar.setVerticalGroup(
			gLayoutTopBar.createSequentialGroup()
			.addGroup(gLayoutTopBar.createParallelGroup(Alignment.TRAILING)
				.addGap(15)
				.addComponent(listTopBarBtns[0], 30, GroupLayout.DEFAULT_SIZE, 60)
				.addComponent(listTopBarBtns[1], 30, GroupLayout.DEFAULT_SIZE, 60)
				.addComponent(listTopBarBtns[2], 30, GroupLayout.DEFAULT_SIZE, 60)
			)
			.addComponent(jPnlBtnSel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 80)
		);
		maxCntPnl = new JPanel();
		GroupLayout layout = new GroupLayout(maxCntPnl);
		maxCntPnl.setLayout(layout);
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(jPnlTopBar)
			)
			.addContainerGap()
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(
				layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jPnlTopBar)
				)
			)
			.addContainerGap()
		);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().add(maxCntPnl, BorderLayout.CENTER);
		// getContentPane().add
		pack();
	}

	private JPanel maxCntPnl;

	private JPanel jPnlTopBar;
	private JPanel jPnlBtnSel;
	
	private List<JComponent> listElesPnlBtnSel;

	private JButton[] listTopBarBtns;
	private JComboBox<String> jCBCiclosUser;
	private JTable jTblListaCursosCiclo;
}