package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

            //            System.out.println("Please enter a command:  (\"Time\" to get time, or \"Echo message\" to get echo) \n>");
            //            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers

//            RegexChecker checker = new RegexChecker();

            //print menu
            Scanner keyboard = new Scanner(System.in);
            boolean loop = true;
            MainMenu menuOption;
            int option;
            while (loop)
            // printMainMenu();
            {
                System.out.println("\nOPtions:");
                for (int i = 0; i < MainMenu.values().length; i++) {
                    System.out.println("\t" + Colours.BLUE + i + ". " + MainMenu.values()[i].toString() + Colours.RESET);

                }
                System.out.println("Select an option (0 to quit): ");//Don't allow duplicates, ID values unique
                try {
                    option = keyboard.nextInt();
                    keyboard.nextLine();
                    menuOption = MainMenu.values()[option];
                    switch (menuOption) {
                        case QUIT_APPLICATION:
                            loop = false;
                            break;
                        case REGISTER:
                            while (true) {
                                try {
                                    System.out.println("Enter CAO Number: ");
                                    String pattern = "^[0-9]{8}$";
                                    int caoNumber = keyboard.nextInt();
                                    String pass = String.valueOf(caoNumber);
                                    if (!RegexChecker.checkRegister(pass, pattern)) {
                                        throw new IllegalArgumentException();
                                    }

                                    System.out.println("Date-Of-Birth");
                                    String dob = keyboard.next();
                                    pattern = "^\\d{4}-\\d{2}-\\d{2}$";
                                    if (!RegexChecker.checkRegister(dob, pattern)) {
                                        throw new IllegalArgumentException();
                                    }

                                    System.out.println("Enter Password");
                                    pattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";
                                    String password = keyboard.next();
                                    if (!RegexChecker.checkRegister(password, pattern)) {
                                        throw new IllegalArgumentException();
                                    }

                                    String command = CAOService.REGISTER_COMMAND + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dob + CAOService.BREAKING_CHARACTER + password;
                                    socketWriter.println(command); //send message to server via socket
                                    Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
                                    String reply = socketReader.nextLine(); // waits for reply
                                    //reply
                                    System.out.println("Reply " + reply);

                                    break;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(Colours.RED + "IllegalArgumentException, Try again" + Colours.RESET);
                                }
                            }
                            break;
                        case LOGIN:
                            while (true) {

                                System.out.println("Enter CAO Number: ");
                                int caoNumber = keyboard.nextInt();
                                System.out.println("Date-Of-Birth");
                                String dob = keyboard.next();
                                System.out.println("Enter Password");
                                String password = keyboard.next();

                                String command = CAOService.LOGIN_COMMAND + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dob + CAOService.BREAKING_CHARACTER + password;
                                socketWriter.println(command); //send message to server via socket
                                Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
                                String reply = socketReader.nextLine(); // waits for reply
                                //reply
                                System.out.println("Reply " + reply);

                                boolean login = studentDaoInterface.loginStudent(new Student(caoNumber, dob, password));
//                                boolean login = false;
//                                if (reply.equals(CAOService.SUCCESSFUL_LOGIN))
//                                    login = true;

                                if (login)
                                    StudentMenu(caoNumber);
                                break;
                            }
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
            System.out.println(Colours.PURPLE + "See you later!!" + Colours.RESET);


        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }


    static void StudentMenu(int caoNumber)
    {
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

            //            System.out.println("Please enter a command:  (\"Time\" to get time, or \"Echo message\" to get echo) \n>");
            //            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers

            Scanner keyboard = new Scanner(System.in);
            boolean loop = true;
            StudentMenu menuOption;
            int option;
            while (loop) {
                System.out.println("\nOPtions:");
                for (int i = 0; i < StudentMenu.values().length; i++) {
                    System.out.println("\t" + Colours.BLUE + i + ". " + StudentMenu.values()[i].toString() + Colours.RESET);

                }
                System.out.println("Select an option (0 to quit): ");//Don't allow duplicates, ID values unique
                try {
                    option = keyboard.nextInt();
                    keyboard.nextLine();
                    menuOption = StudentMenu.values()[option];
                    switch (menuOption) {

                        case LOG_OUT:
                            loop = false;
                            break;
//                        case DISPLAY_ALL_COURSES:
//                            command = CAOService.DISPLAY_ALL_COURSE + CAOService.BREAKING_CHARACTER + caoNumber;;
//                            socketWriter.println(command); //send message to server via socket
//
//                            socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
//
//                            reply = socketReader.nextLine(); // waits for reply
//
//                            //reply
//                            System.out.println("Reply " + reply);
//                            break;
                        case DISPLAY_COURSE:
                            System.out.println("Please enter the course ID:");
                            String courseID = keyboard.next();  // get from user input
                            String command = CAOService.DISPLAY_COURSE + CAOService.BREAKING_CHARACTER + courseID;
                            socketWriter.println(command); //send message to server via socket

                            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply

                            String reply = socketReader.nextLine(); // waits for reply

                            //reply
                            System.out.println("Reply " + reply);
                            break;

                        case DISPLAY_CURRENT_COURSES:
                            command = CAOService.DISPLAY_ALL_COURSE + CAOService.BREAKING_CHARACTER + caoNumber;
                            socketWriter.println(command); //send message to server via socket

                            socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply

                            reply = socketReader.nextLine(); // waits for reply

                            //reply
                            System.out.println("Reply " + reply);
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(Colours.RED + "IllegalArgumentException, Try again" + Colours.RESET);
                    keyboard.nextLine();
                } catch (InputMismatchException ime) {
                    System.out.println(Colours.RED + "InputMismatchException, Try again" + Colours.RESET);
                    keyboard.nextLine();
                }
            }
            System.out.println(Colours.PURPLE + "See you later!!" + Colours.RESET);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static boolean login(int caoNumber, String dateOfBirth, String password) throws DaoException
//    {
//        Student s = studentDao.findStudent(caoNumber);
//
//        if (s == null)
//        {
//            System.out.println(Colours.RED + "CAO Number cannot be found" + Colours.RESET);
//            return false;
//        } else if (!s.getPassword().equals(password))
//        {
//            System.out.println(s.getPassword());
//            System.out.println(Colours.RED + "Incorrect Password" + Colours.RESET);
//            return false;
//        } else if (!s.getDayOfBirth().equals(dateOfBirth))
//        {
//            System.out.println(Colours.RED + "Incorrect Date of Birth" + Colours.RESET);
//            return false;
//        } else if (!s.getDayOfBirth().equals(dateOfBirth) && !s.getPassword().equals(password))
//        {
//            System.out.println(Colours.RED + "Incorrect Date of Birth and Password" + Colours.RESET);
//            return false;
//        } else
//        {
//            return true;
//        }
//    }
}








