package com.dkit.oopca5.core;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    public static final int PORT_NUM = 8080;
    public static final String HOSTNAME = "localhost";

    public static final String BREAKING_CHARACTER = "%%";

    public static final String REGISTER_COMMAND = "REGISTER";
    public static final String SUCCESSFUL_REGISTER = "REGISTERED";
    public static final String FAILED_REGISTER = "REG FAILED";

    public static final String LOGIN_COMMAND = "LOGIN";
    public static final String SUCCESSFUL_LOGIN = "LOGGED IN";
    public static final String FAILED_LOGIN = "LOGIN FAILED";

    public static final String DISPLAY_ALL_COURSE = "ALL COURSES";
    public static final String DISPLAY_COURSE = "COURSE";

//    public static final String CAO_NUMBER = "^[0-9]{8}$" ;
//    public static final String DOB = "^\\d{4}-\\d{2}-\\d{2}$" ;
//    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$" ;
}
