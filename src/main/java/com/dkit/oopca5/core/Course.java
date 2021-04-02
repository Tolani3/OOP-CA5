package com.dkit.oopca5.core;
import java.util.Objects;

public class Course
{
    private String courseID;
    private int level;
    private String title;
    private String institution;

    public Course(String courseID, int level, String title, String institution)
    {
        this.courseID = courseID;
        this.level = level;
        this.title = title;
        this.institution = institution;
    }

    public Course(Course other)
    {
        this(other.courseID,other.level,other.title,other.institution);
    }

    public String getCourseID()
    {
        return courseID;
    }

    public int getLevel()
    {
        return level;
    }

    public String getTitle()
    {
        return title;
    }

    public String getInstitution()
    {
        return institution;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseID, course.courseID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(courseID);
    }

    @Override
    public String toString()
    {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", level=" + level +
                ", title='" + title + '\'' +
                ", institution='" + institution + '\'' +
                '}';
    }
}
