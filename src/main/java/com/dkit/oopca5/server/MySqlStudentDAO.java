package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlStudentDAO extends MySqlDAO implements StudentDaoInterface
{
    @Override
//    public List<Student> findAllStudents() throws DaoException
    public Map<Integer, Student> findAllStudents() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        List<Student> students = new ArrayList<>();
        Map<Integer, Student> students = new HashMap<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM STUDENT";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) {
                int caoNumber = rs.getInt("caoNumber");
                String dob = rs.getString("dateOfBirth");
                String password = rs.getString("password");

                Student stu = new Student(caoNumber, dob, password);
                students.put(caoNumber, stu);


            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUsers() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return students;     // may be empty
    }

    @Override
    public Student findStudent(int caoNumber) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student s = null;

        try {
            con = this.getConnection();

            String query = "SELECT * FROM Student WHERE caoNumber = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, caoNumber);  // search based on the cao number

            rs = ps.executeQuery();
            if (rs.next())
            {
                caoNumber = rs.getInt("caoNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");

                s = new Student(caoNumber, dateOfBirth, password);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return s;     // s may be null
    }


    @Override
    public boolean registerStudent(Student r) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "INSERT INTO STUDENT VALUES (?,?,?)";
            ps = con.prepareStatement(query);

            ps.setInt(1, r.getCaoNumber());
            ps.setString(2, r.getDateOfBirth());
            ps.setString(3, r.getPassword());


            //Using a PreparedStatement to execute SQL - UPDATE...
            success = (ps.executeUpdate() == 1);

        } catch (SQLException e) {
            throw new DaoException("insertStudent() " + e.getMessage());
        } finally {
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
                throw new DaoException("insertStudent() " + e.getMessage());
            }
        }
        return success;
    }

    @Override
    public boolean loginStudent(Student s) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean loginSuccessful = false;
        try
        {
            con = this.getConnection();
            String query = "SELECT * FROM student WHERE cao_number = ? AND date_of_birth = ? AND `password` = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, s.getCaoNumber());
            ps.setString(2, s.getDateOfBirth());
            ps.setString(3, s.getPassword());
            rs = ps.executeQuery();
            if (rs.next())
            {
                loginSuccessful = true;
            }
        } catch (SQLException sq)
        {
            throw new DaoException(Colours.RED + "Login Student Error: " + sq.getMessage() + Colours.RESET);
        } catch (NullPointerException npe)
        {
            System.out.println(Colours.RED+"Cannot be null"+Colours.RESET);
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
                throw new DaoException(Colours.RED + "Finally Closing Error" + sq.getMessage() + Colours.RESET);
            }
        }
        return loginSuccessful;
    }
}






