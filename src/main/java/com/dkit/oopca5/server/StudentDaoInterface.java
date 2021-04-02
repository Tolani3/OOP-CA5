package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;

import java.util.List;
import java.util.Map;

public interface StudentDaoInterface
{
    public Map<Integer, Student> findAllStudents()throws DaoException;
//    public Student findAllStudents()throws DaoException;

    public Student findStudent(int caoNumber)throws DaoException;
    public boolean registerStudent(Student s)throws DaoException;



}
