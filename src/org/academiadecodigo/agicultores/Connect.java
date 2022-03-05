package org.academiadecodigo.agicultores;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Connect implements Runnable {

    private final String name;
    private final DataOutputStream out;
    private final BufferedReader in;
    private final LinkedList<Connect> list;
    private final Socket clientSocket;
    private String select;

    public Connect(Socket clientSocket, LinkedList list) throws IOException {
        this.list = list;
        this.clientSocket = clientSocket;
        Prompt prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
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

    public void sendMsgAll(String receivedMessage) throws IOException {
        for (Connect c : list) {
            c.out.writeUTF(receivedMessage + "\n");
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSelect() {
        return select;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Connect> getList() {
        return list;
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

