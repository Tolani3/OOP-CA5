package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CAOClient
{
    private static Scanner keyboard = new Scanner(System.in);

    //Loading DaoInterfaces
    private static StudentDaoInterface studentDaoInterface = new MySqlStudentDAO();
    private static CourseDaoInterface courseDaoInterface = new MySqlCourseDAO();
    private static ChoicesDaoInterface choicesDaoInterface = new MySqlChoicesDAO();


    public static void main(String[] args)
    {
        CAOClient App = new CAOClient();
        App.start();

        MainMenuLoop();
    }

    private void start()
    {

    }

    public static void MainMenuLoop()
    {
        boolean loop = true;
        MainMenu mainMenu;
        int option;
        while (loop) {
            printMainMenu();
            try {
                option = keyboard.nextInt();
                keyboard.nextLine();
                mainMenu = MainMenu.values()[option];
                switch (mainMenu) {
                    case QUIT_APPLICATION:
                        loop = false;
                        break;
                    case REGISTER:
                        try {
                            System.out.println("Enter CAO Number: ");
                            int caoNumber = keyboard.nextInt();
                            System.out.println("Date-Of-Birth");
                            String dob = keyboard.next();
                            System.out.println("Enter Password");
                            String password = keyboard.next();
                            studentDaoInterface.registerStudent(new Student(caoNumber, dob, password));
                        } catch (DaoException e) {
                            System.out.println(Colours.RED + "InputMismatchException, Try again" + Colours.RESET);
                        }
                        break;
                    case LOGIN:
                        System.out.println("Enter CAO Number: ");
                        int caoNumber = keyboard.nextInt();
                        System.out.println("Date-Of-Birth");
                        String dob = keyboard.next();
                        System.out.println("Enter Password");
                        String password = keyboard.next();
//                        if
//                        (
//                                studentDaoInterface.loginStudent (caoNumber,dob,password)
//                        );
//                            StudentMenu(caoNumber);
//                        break;
                        boolean login = studentDaoInterface.loginStudent(new Student(caoNumber, dob, password));
                        if(login)
                            StudentMenu(caoNumber);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(Colours.RED + "IllegalArgumentException, Try again" + Colours.RESET);
                keyboard.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(Colours.RED + "ArrayIndexOutOfBoundsException, Try again" + Colours.RESET);
                keyboard.nextLine();
            } catch (InputMismatchException ime) {
                System.out.println(Colours.RED + "InputMismatchException, Try again" + Colours.RESET);
                keyboard.nextLine();
            } catch (DaoException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static void StudentMenu(int caoNumber)
    {
        boolean loop = true;
        StudentMenu mainMenu;
        int option;
        while (loop) {
            printStudentMenu();
            try {
                option = keyboard.nextInt();
                keyboard.nextLine();
                mainMenu = StudentMenu.values()[option];
                switch (mainMenu) {
                    case LOG_OUT:
                        loop = false;
                        break;
                    case DISPLAY_ALL_COURSES:
                        System.out.println(courseDaoInterface.getAllCourses());
                        break;
                    case DISPLAY_COURSE:
                        System.out.println("Course ID:");
                        String courseID = keyboard.next();
                        System.out.println(courseDaoInterface.DisplayCourse(courseID));
                        break;

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public static void printMainMenu()
    {
        System.out.println("\nOPtions:");
        for (int i = 0; i < MainMenu.values().length; i++) {
            System.out.println("\t" + Colours.BLUE + i + ". " + MainMenu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Select an option (0 to quit): ");//Don't allow duplicates, ID values unique
    }
    private static void printStudentMenu ()
    {
        System.out.println("\nOPtions:");
        for (int i = 0; i < StudentMenu.values().length; i++)
        {
            System.out.println("\t" + Colours.BLUE + i + ". " + StudentMenu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Select an option (0 to quit): ");//Don't allow duplicates, ID values unique
    }

}
