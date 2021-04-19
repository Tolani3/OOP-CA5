package com.dkit.oopca5.core.johnloane;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDAO;
import com.dkit.oopca5.server.StudentDaoInterface;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class StudentTest
{
    StudentDaoInterface StudentDaoInterface = new MySqlStudentDAO();

    @Test
    public void shouldAnswerWithTrue() {assertTrue(true); }

    @Test
    public void testRegisterStudent() throws DaoException
    {
        int caoNumber = 55555555;
        String dob = "2001-05-04";
        String password = "Password3";
        Student expected = new Student(caoNumber, dob, password);

        StudentDaoInterface.registerStudent(expected);
        Student actual = StudentDaoInterface.findStudent(55555555);

        assertTrue(actual.equals(expected));
    }

    @Test
    public void testLoginStudent() throws DaoException
    {
        int caoNumber = 55555555;
        String dob = "2001-05-04";
        String password = "Password3";
        Student expected = new Student(caoNumber, dob, password);

        StudentDaoInterface.loginStudent(expected);
        Student actual = StudentDaoInterface.findStudent(55555555);

        assertTrue(actual.equals(expected));
    }

//    @Test
//    public void testFindStudent() throws DaoException
//    {
//        Student s = new Student(10000001, "2001-09-04", "Password1");
//        Student s2 = new Student(1, "2001-09-04", "Password1");
//        try
//        {
//            assertNotNull("Get student exists", MySqlStudentDAO.findStudent(s.getCaoNumber()));
//            // student exists in database therefore get back student object
//
//            assertNull("Get student not exists",MySqlStudentDAO.getStudent(s2.getCaoNumber()));
//            //Student does not exist in database therefore returns null
//        } catch (DaoException dao)
//        {
//            dao.printStackTrace();
//        }
//    }





}
