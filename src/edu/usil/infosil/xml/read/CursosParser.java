package edu.usil.infosil.xml.read;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import edu.usil.infosil.notas.dto.CursoDTO;

public class CursosParser {
	static final String CURSO = "curso";
	static final String C_ID = "id";
	static final String C_CODE = "code";
	static final String C_CREDITOS = "creditos";
	static final String CURSOSCNT = "cursos";
	static final String CCNT_SYNC = "sync";
	private List<String> archivosCursos = new ArrayList<String>();

	public CursosParser(String file) {
		archivosCursos.add(file);
	}

	public CursosParser(String[] files) {
		for (String file : files) {
			archivosCursos.add(file);
		}
	}

	/* public LocalDateTime getLastSync(String pathFile) {

	} */

	public List<CursoDTO> readAll(int idx) {
		return readAll(archivosCursos.get(idx));
	}

	public static List<CursoDTO> readAll(String pathFile) {
		List<CursoDTO> cursos = new ArrayList<CursoDTO>();
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = new FileInputStream(pathFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			CursoDTO curso = null;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(CURSO)) {
						curso = new CursoDTO();
						// curso.setId();
						Iterator<Attribute> atributos = event.asStartElement().getAttributes();
						while (atributos.hasNext()) {
							Attribute atributo = atributos.next();
							if (atributo.getName().toString().equals(C_ID)) {
								try {
									curso.setId(Integer.parseInt(atributo.getValue()));
								} catch (Exception e) {
									System.out.println("C_ID: valor no entero > " + atributo.getValue());
								}
							} else if (atributo.getName().toString().equals(C_CODE)) {
								curso.setCode(atributo.getValue());
							} else if (atributo.getName().toString().equals(C_CREDITOS)) {
								try {
									curso.setCreditos(Integer.parseInt(atributo.getValue()));
								} catch (Exception e) {
									System.out.println("C_CREDITOS: valor no entero > " + atributo.getValue());
								}
							}
						}
						event = eventReader.nextEvent();
						curso.setNombre(event.asCharacters().getData());
					}
				}
				if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart().equals(CURSO)) {
						cursos.add(curso);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return cursos;
	}

}
