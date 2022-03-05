package org.academiadecodigo.agicultores;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Game {

    private final Connect connect;
    private final Connect player;
    private final Connect chosenPlayer;

    private final String[] options;

    public Game(Connect connect) throws IOException {
        this.connect = connect;
        int size = connect.getList().size();
        options = new String[size];
        player = connect;

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
    }

    public void fMenu() throws IOException {
        for (Connect c : connect.getList()) {
            if (c.getName().equals(chosenPlayer.getName())) {
                Prompt prompt = new Prompt(chosenPlayer.getClientSocket().getInputStream(), new PrintStream(chosenPlayer.getClientSocket().getOutputStream()));

                MenuInputScanner scanner = new MenuInputScanner(options);
                scanner.setMessage("Who do you want to screw??");

                int answerIndex = prompt.getUserInput(scanner);
                answerIndex -= 1;

                System.out.println("User wants to " + options[answerIndex]);
                player.setSelect(options[answerIndex]);
                System.out.println("O resultado:" + player.getSelect());
                sMenu(player.getSelect());

                break;
            }
        }
    }

    public void sMenu(String playerSelect) throws IOException {
        int random = (int) (Math.random() * 16);
        String[] dares = {"Sentar no colo do tio rolo!",
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
        connect.sendMsgAll("\n" + playerSelect + ", " + dares[random]);
    }
}
