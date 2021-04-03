package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import com.dkit.oopca5.core.Colours;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CAOClient
{
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args)
    {
        CAOClient App = new CAOClient();
        App.start();
    }

    private void start()
    {
        MainMenuLoop();
    }

    private void MainMenuLoop()
    {
        boolean loop = true;

        MainMenu menuOption;
        int option;
        while (loop) {
            printMainMenu();
            try {
                option = keyboard.nextInt();
                keyboard.nextLine();
                menuOption = MainMenu.values()[option];
                switch (menuOption)
                {
                    case QUIT_APPLICATION:
                        loop = false;
                        break;
                    case REGISTER:
                        registerStudent();
                        break;
                    case LOGIN:
                        ComputerMenuLoop();
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
            }
        }
    }


    public static void printMainMenu()
    {
        System.out.println("\nOPtions:");
        for (int i = 0; i < MainMenu.values().length; i++)
        {
            System.out.println("\t" + Colours.BLUE + i + ". " + MainMenu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Select an option (0 to quit): ");//Don't allow duplicates, ID values unique
    }
}
