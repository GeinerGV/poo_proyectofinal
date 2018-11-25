package test;

import java.util.List;

import edu.usil.infosil.notas.NotasFrame;
import edu.usil.infosil.notas.dto.CursoDTO;
import edu.usil.infosil.xml.read.CursosParser;
import externo.writerXML;

public class Tester {
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NotasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NotasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NotasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NotasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NotasFrame().setVisible(true);
				/* 
				CursosParser cparser= new CursosParser();
				List<CursoDTO> listaCursosAlmacenados = cparser.read("src/test/recursos/xml/Cursos.xml");
				for (CursoDTO curso : listaCursosAlmacenados) {
					System.out.println(curso);
				}
				 */
			}
		});
	}
}