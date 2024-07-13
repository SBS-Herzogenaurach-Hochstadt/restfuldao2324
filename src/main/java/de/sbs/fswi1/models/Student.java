package de.sbs.fswi1.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Student extends StudentDTO {

    public Student(String vorname, String nachname, String geburtsdatum, String klasse) {
        super(vorname, nachname, geburtsdatum, klasse);
    }

    public String getNachnameVorname() {
        return String.format("%s, %s", nachname, vorname);
    }

    public int getAlterInJahrenZuHeute() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate bufGeburtsdatum = LocalDate.parse(geburtsdatum, formatter);
        LocalDate heute = LocalDate.now();
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

        return otherStudent.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}