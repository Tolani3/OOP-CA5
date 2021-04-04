package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;

import java.util.List;

public interface ChoicesDaoInterface
{
    public List<String> findCourseChoice(int caoNumber)throws DaoException;

}
