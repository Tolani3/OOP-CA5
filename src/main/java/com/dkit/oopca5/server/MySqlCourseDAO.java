package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDAO extends MySqlDAO implements CourseDaoInterface
{
    @Override
    public List<Course> getAllCourses() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Course> courseList = new ArrayList<>();
        try
        {
            con = this.getConnection();
            String query = "SELECT * FROM COURSE;";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next())
            {
                String courseID = rs.getString("courseID");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                courseList.add(new Course(courseID, level, title, institution));
            }
        } catch (SQLException sq)
        {
            throw new DaoException(Colours.RED + "Get Course Error: " + sq.getMessage() + Colours.RESET);
        } catch (NullPointerException npe)
        {
            System.out.println(Colours.RED + "Cannot be null" + Colours.RESET);
        } finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException sq)
            {
                throw new DaoException(Colours.RED + "Error: " + sq.getMessage() + Colours.RESET);
            }
        }
        return courseList;
    }
}
