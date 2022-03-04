package org.academiadecodigo.agicultores;

import org.academiadecodigo.bootcamp.InputScanner;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;

public class Game {
    private Connect connect;
    private String[] options;
    private int size = 0;
    private Socket clientSocket;
    private Prompt prompt;
    private MenuInputScanner scanner;

    public Game(Connect connect, Socket clientSocket) throws IOException {
        this.connect = connect;
        this.clientSocket = clientSocket;
        size = connect.getList().size();
        options = new String[size];

        int counter = 0;
        System.out.println(this.connect.getList().size());
        System.out.println(this.connect.getList().get(0));
        System.out.println(this.connect.getList().get(1));
        for (Connect c : this.connect.getList()) {
            String s2 = c.getName();
            options[counter] = s2;
            counter++;
        }

        Collections.shuffle(connect.getList());
        Connect chosenPlayer = connect.getList().get(0);
        System.out.println(chosenPlayer.getName());

        for (Connect c : connect.getList()) {
            if(c.getName().equals(chosenPlayer.getName())) {

                prompt = new Prompt(chosenPlayer.getClientSocket().getInputStream(), new PrintStream(chosenPlayer.getClientSocket().getOutputStream()));
                scanner = new MenuInputScanner(options);
                scanner.setMessage("Should I stay or should I go?");
                int answerIndex = prompt.getUserInput(scanner);
                System.out.println("User wants to " + options[answerIndex - 1]);

            }
        }
    }
}
