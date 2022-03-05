package org.academiadecodigo.agicultores;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;

public class Game {
    private Socket clientSocket;
    private Connect connect;
    private Connect teste;
    private Connect chosenPlayer;

    private String[] options;
    private String[] truthDare = {"True","Dare"};

    private int size = 0;

    private Prompt prompt;

    private MenuInputScanner scanner;

    private int answerIndex;

    public Game(Connect connect,Socket clientSocket) throws IOException {
        this.connect = connect;
        this.clientSocket = clientSocket;
        size = connect.getList().size();
        options = new String[size];
        this.teste = connect;

        int counter = 0;
        for (Connect c : this.connect.getList()) {
            String s2 = c.getName();
            options[counter] = s2;
            counter++;
        }

        Collections.shuffle(connect.getList());
        chosenPlayer = connect.getList().get(0);
        System.out.println(chosenPlayer.getName());
        fMenu();
        sMenu();
    }
    public void fMenu()throws IOException{
        for (Connect c : connect.getList()) {
            if(c.getName().equals(chosenPlayer.getName())) {
                prompt = new Prompt(chosenPlayer.getClientSocket().getInputStream(), new PrintStream(chosenPlayer.getClientSocket().getOutputStream()));
                scanner = new MenuInputScanner(options);
                scanner.setMessage("Should I stay or should I go?");
                answerIndex = prompt.getUserInput(scanner);
                answerIndex -=1;
                System.out.println("User wants to " + options[answerIndex]);
                teste.setSelect(options[answerIndex]);
                System.out.println("O resultado:" + teste.getSelect());
                break;
            }
        }
    }

    public void sMenu()throws IOException{
        for (Connect o : connect.getList()) {
            if (o.getName().equals(teste.getSelect())) {
                prompt = new Prompt(o.getClientSocket().getInputStream(), new PrintStream(o.getClientSocket().getOutputStream()));
                scanner = new MenuInputScanner(truthDare);
                scanner.setMessage("Choose:");
                answerIndex = prompt.getUserInput(scanner);
                System.out.println("User wants to " + truthDare[answerIndex-1]);

                switch (answerIndex){
                    case 1:
                        ogoncalochupa();
                        break;
                    case 2:
                        int random = (int) (Math.random()*16);

                        String[] dares = { "Sentar no colo do tio rolo!" ,
                                " Mandar um site russo baixo",
                                "Perder uma oportunidade",
                                "10 flexões de cu pro ar ",
                                "Mão direta copo cheio e penálti….",
                                "Deste uma de Elder 9:31 crew ",
                                "Levar tau tau da Raquel",
                                "Descobrir quem é o pai biológico do 'filho' do Rodrigo.",
                                "Esticar o cabelo ao Pedro (if you can)",
                                "Levar o Tim a um puteiro <3",
                                "Ligar ao Diogo (912871506) e pedir desculpa!",
                                "O summerizer e teu! ",
                                "Dar Explicações ao burro do Gonçalo",
                                "Perguntar a alguém se sabe seopalovem",
                                "Ir a fão sem GPS",
                                "chupo gaita",
                        };
                        connect.sendMsgAll(dares[random]);
                        break;
                }
                break;
            }
        }

    }

    public void ogoncalochupa() throws IOException {
        prompt = new Prompt(chosenPlayer.getClientSocket().getInputStream(), new PrintStream(connect.getClientSocket().getOutputStream()));
        StringInputScanner question1 = new StringInputScanner();
        question1.setMessage("What's the question?");
        String answer = prompt.getUserInput(question1);
        System.out.println(answer);
    }
}
