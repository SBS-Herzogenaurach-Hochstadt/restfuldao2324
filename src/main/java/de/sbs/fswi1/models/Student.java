package de.sbs.fswi1.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Student extends StudentDTO {

    private Timestamp zeitstempel;

    public Student(String vorname, String nachname, String geburtsdatum, String klasse) {
        super(vorname, nachname, geburtsdatum, klasse);
        zeitstempel = new Timestamp(System.nanoTime());
    }

    public long getZeitstempel() {
        return zeitstempel.getTime();
    }

    public String getNachnameVorname() {
        return String.format("%s, %s", nachname, vorname);
    }

    public int getAlterInJahrenZuHeute() {
        // Das Format des Datums festlegen
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        // String in ein LocalDate-Objekt umwandeln
        LocalDate bufGeburtsdatum = LocalDate.parse(geburtsdatum, formatter);
        // Das aktuelle Datum
        LocalDate heute = LocalDate.now();
        // Das berechnete Alter zurückgeben
        return Period.between(bufGeburtsdatum, heute).getYears();
    }

    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Student otherStudent = (Student) other;

        if (otherStudent.getVorname().equals(this.getVorname())
                && otherStudent.getNachname().equals(this.getNachname())
                && otherStudent.getGeburtsdatum().equals(this.getGeburtsdatum())
                && otherStudent.getKlasse().equals(this.getKlasse())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorname, nachname, geburtsdatum, klasse);
    }
}