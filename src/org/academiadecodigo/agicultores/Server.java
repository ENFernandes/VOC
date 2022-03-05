package org.academiadecodigo.agicultores;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int PORT = 8080;
    private Connect connect;
    private int countPlayer = 0;
    private final LinkedList<Connect> list = new LinkedList<>();

    public static void main(String[] args) {
        Server server = new Server();
        int players = args.length == 0 ? 4 : Integer.parseInt(args[0]);
        server.dispatch(PORT, players);
    }

    public void dispatch(int port, int players) {
        try {
            Socket clientSocket;
            ServerSocket bindSocket = new ServerSocket(port);
            while (!bindSocket.isClosed()) {
                if (countPlayer < players) {
                    clientSocket = bindSocket.accept();
                    Thread thread = new Thread(connect);
                    connect = new Connect(clientSocket, list);
                    list.add(connect);
                    thread.start();
                    countPlayer++;
                } else {
                    new Game(connect);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro nas Ligaçoes");
        }
    }
}
