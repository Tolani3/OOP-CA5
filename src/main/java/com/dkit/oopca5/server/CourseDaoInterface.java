package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;

import java.util.List;

public interface CourseDaoInterface
{
    public List<Course> getAllCourses() throws DaoException;
    public Course DisplayCourse(String courseID) throws DaoException;
//    public boolean isCourseAvailable(String courseID) throws DaoException;


}
