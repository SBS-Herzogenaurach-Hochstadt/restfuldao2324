package de.sbs.fswi1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import de.sbs.fswi1.models.StudentDTO;
import de.sbs.fswi1.services.DataAccessObject;

public class Main {
    // Klassen-Scope
    private static String pfadAsString = "C:/Users/cgg/Documents/data/studenten.csv";
    private static Path pfadAsPath = Path.of("C:/Users/cgg/Documents/data/studentenAusListe.json");

    public static void main(String[] args) {
        // main-Methoden-Scope

        Path pfadAufCSV = Path.of(pfadAsString);
        try {
            String csvContent = Files.readString(pfadAufCSV);
            if (csvContent != null) {
                createJson(csvContent);
            }
        } catch (Exception ignored) {
        }

        createJsonAusListe();
    }

    private static void createJsonAusListe() {

        // im Kopf Schritte bestimmen, die nötig sind!!!!!!
        // Schritt 1: DAO erzeugen
        DataAccessObject dao = new DataAccessObject(pfadAsString);

        // Schrtitt 2: dao bnutzen, um List<Student>
        List<StudentDTO> liste = dao.findAll();

        // Schritt 3
        // new StringBuilder-Objekt vorbelegt mit "["
        StringBuilder json = new StringBuilder("[");

        // Schritt 4
        // Festlegung "Key":"Value" mit Platzhalter %s für das spätere Value
        String format = "{\"vorname\":\"%s\",\"nachname\":\"%s\",\"geburtsdatum\":\"%s\",\"klasse\":\"%s\"}";
        
        // Schritt 5 Schleife 
        for (int i = 0; i < liste.size(); i++) {
            // Zugriff auf die StudentenDTO-Objekte der Liste über den Index der Schleife         
            json.append(String.format(format, liste.get(i).getVorname(),
            liste.get(i).getNachname(),
            liste.get(i).getGeburtsdatum(),
            liste.get(i).getKlasse()));
            
            if (i < liste.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        saveToFile(json);
    }

    private static void createJson(String csvContent) {
        String[] lines = csvContent.split("\n");

        // new StringBuilder-Objekt vorbelegt mit "["
        StringBuilder json = new StringBuilder("[");

        // Festlegung "Key":"Value" mit Platzhalter %s für das spätere Value
        String format = "{\"vorname\":\"%s\",\"nachname\":\"%s\",\"geburtsdatum\":\"%s\",\"klasse\":\"%s\"}";

        for (int i = 0; i < lines.length; i++) {
            String[] items = lines[i].split(",");
            if (items.length == 4) {
                json.append(String.format(format, items[0], items[1], items[2], items[3].trim()));
            }
            if (i < lines.length - 1) {
                json.append(",");
            }
        }
        json.append("]");

        saveToFile(json);
    }

    private static void saveToFile(StringBuilder json) {
        try {
            Files.writeString(pfadAsPath, json.toString());
        } catch (Exception ignored) {
        }
    }
}