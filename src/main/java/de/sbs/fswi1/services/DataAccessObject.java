package de.sbs.fswi1.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import de.sbs.fswi1.models.StudentDTO;

public class DataAccessObject {

	private final String pathAsString;
	private final List<StudentDTO> studenten;

	public DataAccessObject(String pathAsString) {
		this.pathAsString = pathAsString;
		studenten = new ArrayList<>();
	}

	public List<StudentDTO> findAll() {
		Path pfadAufCSV = Path.of(pathAsString);
		try {
			String csvContent = Files.readString(pfadAufCSV);
			String[] lines = csvContent.split("\n");
			for (String line : lines) {
				String[] elements = line.split(",");
				studenten.add(new StudentDTO(elements[0], elements[1], elements[2], elements[3]));
			}
		} catch (Exception ignored) {
		}
		return studenten;
	}

	public boolean save(StudentDTO student) {
		Path pfadAufCSV = Path.of(pathAsString);
		try {
			String contentToWrite = String.format("%s,%s,%s,%s%n", student.getVorname(), student.getNachname(), student.getGeburtsdatum(), student.getKlasse());
			Files.writeString(pfadAufCSV, contentToWrite, StandardOpenOption.APPEND);
			return true;
		} catch (Exception ignored) {			
		}
		return false;	
	}
}