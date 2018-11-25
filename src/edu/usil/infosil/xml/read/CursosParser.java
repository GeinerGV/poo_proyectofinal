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
	static final String ID = "id";
	static final String CODE = "code";
	static final String CREDITOS = "creditos";

	public List<CursoDTO> read(String pathFile) {
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
							if (atributo.getName().toString().equals(ID)) {
								try {
									curso.setId(Integer.parseInt(atributo.getValue()));
								} catch (Exception e) {
									System.out.println("ID: valor no entero > " + atributo.getValue());
								}
							} else if (atributo.getName().toString().equals(CODE)) {
								curso.setCode(atributo.getValue());
							} else if (atributo.getName().toString().equals(CREDITOS)) {
								try {
									curso.setCreditos(Integer.parseInt(atributo.getValue()));
								} catch (Exception e) {
									System.out.println("CREDITOS: valor no entero > " + atributo.getValue());
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