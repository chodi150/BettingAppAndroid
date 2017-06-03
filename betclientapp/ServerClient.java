package chodi.com.betclientapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import chodi.Match;
import chodi.UserPointsPair;

/**
 * Created by Użytkownik on 21.02.2017.
 */

public class ServerClient {

    ObjectInputStream inFromServer = null;


    public boolean login(Socket clientSocket, String username, String password) throws IOException
    {
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());

        outToServer.writeObject(username);
        outToServer.writeObject(password);

        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        boolean answer = false;
        try {
            answer = (boolean)inFromServer.readObject();
        } catch (ClassNotFoundException e) {
            throw new Error("Can't connect to the server");
        }

        return answer;
    }

    public boolean sendTypesToServer(Socket clientSocket, int[][] types)
    {
        ObjectOutputStream outToServer;
        try {
            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(types);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public  ArrayList<Match> receiveScheduledMatchesFromServer(Socket clientSocket) throws IOException
    {
        if(inFromServer==null)
            inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        ArrayList<Match> matches;
        try {
            matches = (ArrayList<Match>)inFromServer.readObject();
        } catch (ClassNotFoundException e) {
            throw new Error("Problems while receiving data from server");
        }

        return matches;
    }


    public  ArrayList<UserPointsPair> receiveRankOfPlayersFromServer(Socket clientSocket) throws IOException
    {
        if(inFromServer==null)
            inFromServer= new ObjectInputStream(clientSocket.getInputStream());

        ArrayList<UserPointsPair> rank;
        try {
            rank = (ArrayList<UserPointsPair>)inFromServer.readObject();
        } catch (ClassNotFoundException e) {
            throw new Error("Problems while receiving data from server");
        }

        return rank;
    }


}
