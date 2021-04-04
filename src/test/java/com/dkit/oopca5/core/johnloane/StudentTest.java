package com.dkit.oopca5.core.johnloane;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDAO;
import com.dkit.oopca5.server.StudentDaoInterface;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void testFindStudent() throws DaoException
    {

    }





}
