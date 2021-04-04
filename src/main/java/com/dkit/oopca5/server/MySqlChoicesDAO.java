package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Choices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlChoicesDAO extends MySqlDAO implements ChoicesDaoInterface
{


    @Override
    public Choices findCourseChoice(int caoNumber) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Choices c = null;

        try {
            con = this.getConnection();

            String query = "SELECT * FROM choices WHERE caoNumber = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, caoNumber);  // search based on the cao number

            rs = ps.executeQuery();
            if (rs.next())
            {
                caoNumber = rs.getInt("caoNumber");
                String courseID = rs.getString("courseID");

                c = new Choices(caoNumber, courseID);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return c;     // s may be null
    }
}
