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
        } catch (SQLException e) {
            throw new DaoException("findAllUsers() " + e.getMessage());
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
            if (rs.next()) {
                caoNumber = rs.getInt("CAONUMBER");
                String dateOfBirth = rs.getString("DATEOFBIRTH");
                String password = rs.getString("PASSWORD");

                s = new Student(caoNumber, dateOfBirth, password);
            }
        } catch (SQLException e) {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
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
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return s;     // s may be null
    }


    @Override
    public boolean registerStudent(Student s) throws DaoException
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

            ps.setInt(1, s.getCaoNumber());
            ps.setString(2, s.getDateOfBirth());
            ps.setString(3, s.getPassword());


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
}






