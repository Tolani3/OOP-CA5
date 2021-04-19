package com.dkit.oopca5.server;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CAOClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket socket;
    int clientNumber;
    MySqlStudentDAO studentDao = new MySqlStudentDAO();

    public CAOClientHandler(Socket socket, int clientNumber)
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
