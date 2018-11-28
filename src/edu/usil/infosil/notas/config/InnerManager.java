package edu.usil.infosil.notas.config;

public class InnerManager {

	private static String directorio_base = "";

	private static final String[] DIRECTORIOS = {"src/test/recursos/xml/Cursos.xml"};

	public static String getDownloadCursosPath() {
		return DIRECTORIOS[0];
	}

	// TODO: Ejemplos de como configurar el diseño de la ventana
	private static final int DEFAULT_INIT_TAB = 1;
	
}