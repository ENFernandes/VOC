package org.academiadecodigo.agicultores;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int DEFAULT_PORT = 8080;
    private ServerSocket bindSocket = null;
    private Socket clientSocket;
    private Connect connect;
    private Thread thread;
    private Prompt prompt;
    private LinkedList<Connect> list = new LinkedList<>();

    public static void main(String[] args) {

        Server server = new Server();
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        server.dispatch(port);


    }

    public void dispatch(int port) {
        try {
            clientSocket = new Socket();
            bindSocket = new ServerSocket(port);
            while (!bindSocket.isClosed()) {
                clientSocket = bindSocket.accept();
                connect = new Connect();
                thread = new Thread(connect);
                thread.start();
            }
        } catch (IOException e) {
        }
    }

    public class Connect implements Runnable {

        private String name;
        private DataOutputStream out;
        private BufferedReader in;


        public Connect() throws IOException {
            list.add(this);
            prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
            this.out = new DataOutputStream(clientSocket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringInputScanner userName = new StringInputScanner();
            userName.setMessage("UserName: ");
            name = prompt.getUserInput(userName);
            System.out.println("O " + name + " ta on!");
        }

        public void seeAllClient(String receivedMessage) throws IOException {
            for (Connect c : list) {
                if (!c.equals(this)) {
                    c.out.writeUTF(name + ": " + receivedMessage + "\n");
                }
            }
        }

        @Override
        public void run() {
            try {
                String receivedMessage;
                while (!clientSocket.isClosed()) {
                    receivedMessage = in.readLine();
                    seeAllClient(receivedMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
