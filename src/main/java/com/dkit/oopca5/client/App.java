package com.dkit.oopca5.client;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.CourseDaoInterface;
import com.dkit.oopca5.server.MySqlCourseDAO;
import com.dkit.oopca5.server.MySqlStudentDAO;
import com.dkit.oopca5.server.StudentDaoInterface;

import java.util.List;

public class App
{

    public static void main(String[] args)
    {
        StudentDaoInterface studentDao = new MySqlStudentDAO();
        CourseDaoInterface courseDao = new MySqlCourseDAO();


        try
        {
            //TODO Find All Students
            List<Student> studentList = studentDao.findAllStudents();
            System.out.println(studentList);

            // TODO Find All Courses
            List<Course> courseList = courseDao.getAllCourses();
            System.out.println(courseList);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
    }
}
