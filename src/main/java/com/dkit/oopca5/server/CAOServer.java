package com.dkit.oopca5.server;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class CAOServer
{
    public static void main(String[] args)
    {
        CAOServer server = new CAOServer();
        server.start();
    }

    private static void start()
    {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server Message: Server started. Listening for connections...");
            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) {
                Socket socket = ss.accept();  // wait for client to connect, and open a socket with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread thread = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                thread.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e)
        {
            System.out.println("Server Message: IOException: " + e);
        }
    }

    public static class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
    {
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        MySqlStudentDAO studentDao = new MySqlStudentDAO();

        public ClientHandler(Socket socket, int clientNumber)
        {
            try {
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                this.socketReader = new BufferedReader(isReader);
                OutputStream os = socket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer
                this.clientNumber = clientNumber;  // ID number that we are assigning to this client
                this.socket = socket;  // store socket ref for closing

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    if (message.startsWith("REGISTER")) {
                        System.out.println("Client has asked to register with message details" + message);
                        //call DAO to register
                        //assume seccess
                        //split() the message string int parts using %% as delimiter
                        String[] messageArr = message.split("%%");
                        int caoNumber = Integer.parseInt(messageArr[1]);
                        String dob = messageArr[2];
                        String pw = messageArr[3];
                        boolean successful = studentDao.registerStudent(new Student(caoNumber, dob, pw));


                        if (successful)
                            socketWriter.println("REGISTERED");  // sends message to client
                        else
                            socketWriter.println("REG FAILED");
                    } else if (message.startsWith("Time")) {
                        LocalTime time = LocalTime.now();
                        socketWriter.println(time);  // sends current time to client
                    } else if (message.startsWith("Echo")) {
                        message = message.substring(5); // strip off the 'Echo ' part
                        socketWriter.println(message);  // send message to client
                    } else {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }
                }
                socket.close();
            } catch (IOException | DaoException ex) {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }
}
    


    
