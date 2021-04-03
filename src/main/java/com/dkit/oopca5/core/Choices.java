package com.dkit.oopca5.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Choices
{
    private int caoNumber;
    private List<String> courseChoices;

    public Choices(int caoNumber, List<String> courseChoices)
    {
        this.caoNumber = caoNumber;
        this.courseChoices = courseChoices;
    }

    public Choices(int caoNumber)
    {
        this.caoNumber = caoNumber;
        this.courseChoices = new ArrayList<>();
    }

    public int getCaoNumber()
    {
        return caoNumber;
    }

    public List<String> getChoices()
    {
        return courseChoices;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choices that = (Choices) o;
        return caoNumber == that.caoNumber;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(caoNumber);
    }

    @Override
    public String toString()
    {
        return "Choices{" +
                "caoNumber=" + caoNumber +
                ", courseChoices=" + courseChoices +
                '}';
    }
}
