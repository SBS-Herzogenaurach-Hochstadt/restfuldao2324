package de.sbs.fswi1;

import de.sbs.fswi1.models.StudentDTO;
import de.sbs.fswi1.services.DataAccessObject;

public class Main {

    public static void main(String[] args) {

        DataAccessObject dao = new DataAccessObject();

       // StudentDTO student = new StudentDTO("Peter", "Lustig", "01.01.2000", "FSWI-1");
       // dao.save(student);

        System.out.println(dao.findAll().get(5).getId());
    }
}