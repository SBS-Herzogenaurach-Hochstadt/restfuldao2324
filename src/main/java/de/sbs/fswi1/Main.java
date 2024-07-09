package de.sbs.fswi1;

import de.sbs.fswi1.models.StudentDTO;
import de.sbs.fswi1.services.DataAccessObject;

public class Main {

    public static void main(String[] args) {

        DataAccessObject dao = new DataAccessObject();

       StudentDTO student = new StudentDTO("Peter", "Lustig", "01.01.2000", "FSWI-1");
       dao.update(54, student);

        //System.out.println(dao.findAll().size());

        //dao.delete(student.getId());

        //System.out.println(dao.findAll().size());
    }
}