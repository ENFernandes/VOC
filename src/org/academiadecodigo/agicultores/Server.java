package org.academiadecodigo.agicultores;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int PORT = 8080;
    private ServerSocket bindSocket = null;
    private Socket clientSocket;
    private Connect connect;
    private Thread thread;
    private int countPlayer = 0;
    private LinkedList<Connect> list = new LinkedList<>();

    public static void main(String[] args) {

        Server server = new Server();
        int players = args.length == 0 ? 3 : Integer.parseInt(args[0]) ;
        server.dispatch(PORT, players);
    }

    public void dispatch(int port, int players) {
        try {
            clientSocket = new Socket();
            bindSocket = new ServerSocket(port);
            while (!bindSocket.isClosed()) {
                if(countPlayer < players) {
                    clientSocket = bindSocket.accept();
                    thread = new Thread(connect);
                    connect = new Connect(clientSocket,list);
                    list.add(connect);
                    thread.start();
                    countPlayer++;
                }else{

                    new Game(connect,clientSocket);
                }
            }
        }
        catch (IOException e) {
        }
    }
}
