package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Choices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlChoicesDAO extends MySqlDAO implements ChoicesDaoInterface
{
    @Override
    public List<String> findCourseChoice(int caoNumber) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Choices c = null;
        List<String> choicesList = new ArrayList<>();

        try {
            con = this.getConnection();

            String query = "SELECT * FROM choices WHERE caoNumber = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, caoNumber);  // search based on the cao number
            rs = ps.executeQuery();
            while (rs.next())
            {
                choicesList.add(rs.getString(2));
            }
            if (choicesList.size() != 0)
                c = new Choices(caoNumber, choicesList); // Don't make an object if doesn't exist
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
        return choicesList;     // s may be null
    }
}
