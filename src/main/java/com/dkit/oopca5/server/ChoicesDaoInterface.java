package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Choices;

public interface ChoicesDaoInterface
{
    public Choices findCourseChoice(int caoNumber)throws DaoException;

}
