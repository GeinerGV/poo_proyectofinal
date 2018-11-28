package edu.usil.infosil.notas;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

// import edu.usil.infosil.notas.config.InnerManager;

public class NotasFrame extends JFrame {

	public NotasFrame() {
		super("Administrador de Notas");
		initComponents();
	}

	private void initComponents() {
		jPnlMenuTabsBlq = new JPanel();
		jPnlWorkAreaBlq = new JPanel();
		jSplitMenuTabs_WorkArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jPnlMenuTabsBlq, jPnlWorkAreaBlq);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		Container contenedorBase = getContentPane();
		contenedorBase.add(jSplitMenuTabs_WorkArea);
		setMinimumSize(new Dimension(500, 300));
	}

	// TODO: Elementos de la estructura general
	private JPanel jPnlMenuTabsBlq;
	private JPanel jPnlWorkAreaBlq;
	private JSplitPane jSplitMenuTabs_WorkArea;
	private JTabbedPane jTabsPnlNavbar;
	private JTabbedPane jTabsPnlItmsWorkArea;

	// TODO: Elementos para bloque de la barra de navegación
}