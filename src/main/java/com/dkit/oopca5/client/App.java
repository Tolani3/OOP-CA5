package com.dkit.oopca5.client;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDAO;
import com.dkit.oopca5.server.StudentDaoInterface;

import java.util.List;

public class App
{

    public static void main(String[] args)
    {
        StudentDaoInterface studentDao = new MySqlStudentDAO();

        try
        {
            List<Student> studentList = studentDao.findAllStudents();
            System.out.println(studentList);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
    }
}
