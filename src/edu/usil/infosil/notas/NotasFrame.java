package edu.usil.infosil.notas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import edu.usil.infosil.xml.read.CursosParser;

import com.placeholder.PlaceHolder;

public class NotasFrame extends JFrame {

	private int btnSel;
	private final Color[] colores = {Color.gray, Color.orange};
	
	public NotasFrame() {
		btnSel = -1;
		setTitle("Administrador de Notas");
		initComponents();
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
			JComboBox<String> jCBsearcher = new JComboBox<>();
			jCBsearcher.setEditable(true);
			jCBsearcher.setModel(new DefaultComboBoxModel<>(new String[] {}));
			jCBsearcher.setSelectedItem("");
			JButton jBtnSearch = new JButton("Buscar");

			JLabel jLblPerido = new JLabel("Periodo Académico:");
			JTextField jTxtfPerido = new JTextField();
			PlaceHolder holder = new PlaceHolder(jTxtfPerido, "2018-02, 2018-2, 18-2, ...");

			gLayoutPnlBtnSel.setHorizontalGroup(
				gLayoutPnlBtnSel.createSequentialGroup()
				.addGap(20)
				.addComponent(jCBsearcher, (30+175*3+30), GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(5)
				.addComponent(jBtnSearch)
				.addGap(20)
			);
			gLayoutPnlBtnSel.setVerticalGroup(
				gLayoutPnlBtnSel.createParallelGroup()
				.addGap(10)
				.addComponent(jCBsearcher, 35, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(jBtnSearch, 35, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(10)
			);
			// listElesPnlBtnSel.add(new JComboBox<>());
			// listElesPnlBtnSel.get(0).
			jBtnSearch.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					String strParaCurso = ((String)jCBsearcher.getSelectedItem()).trim();
					int selCurso = jCBsearcher.getSelectedIndex();
					if (selCurso==-1 && strParaCurso.length()>0) {

						CursosParser cparser= new CursosParser();
						List<CursoDTO> listaCursosAlmacenados = cparser.read("src/test/recursos/xml/Cursos.xml");
						List<CursoDTO> cursosCoincidentes = new ArrayList<CursoDTO>();
						List<String> concidencias = new ArrayList<String>();

						for (CursoDTO curso : listaCursosAlmacenados) {
							if (strParaCurso.length()>=3) {
								if ((curso.getNombre()).toLowerCase().contains(strParaCurso.toLowerCase())) {
									cursosCoincidentes.add(curso);
								}
							} else if ((curso.getNombre()).toLowerCase().startsWith(strParaCurso.toLowerCase())) {
								cursosCoincidentes.add(curso);
							}
						}

						NotasImpl impl = new NotasImpl();
						cursosCoincidentes.addAll(impl.getSomeCursosCoincidentes(strParaCurso, cursosCoincidentes));
						Collections.sort(cursosCoincidentes, new Comparator<CursoDTO>() {
							@Override
							public int compare(CursoDTO lhs, CursoDTO rhs) {
								return lhs.getId() > rhs.getId() ? 1 : (lhs.getId()<rhs.getId() ? -1 : 0);
							}
						});
						for (CursoDTO curso : cursosCoincidentes) {
							concidencias.add(curso.getNombre() + " - " + curso.getCode());
						}

						jCBsearcher.setModel(new DefaultComboBoxModel<>(concidencias.toArray(new String[concidencias.size()])));
						jCBsearcher.setSelectedItem(strParaCurso);
						if (jCBsearcher.getItemCount()>0) jCBsearcher.showPopup();
					}
				}
			});
		}
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
	
	private List listElesPnlBtnSel;

	private JButton[] listTopBarBtns;
	private JComboBox<String> jCBCiclosUser;
	private JTable jTblListaCursosCiclo;
}