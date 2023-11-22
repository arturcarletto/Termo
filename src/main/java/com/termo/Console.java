package com.termo;

import java.util.Scanner;

public class Console {
    private Game game;
    private final Scanner scanner = new Scanner(System.in);

    public void startGame() {
        while (game == null) {
            System.out.println("Quantas palavras você vai querer advinhar ao mesmo tempo?");
            try {
                game = new Game(Integer.parseInt(scanner.nextLine()));
            } catch (NumberFormatException nfe) {
                System.out.println("Resposta inválida");
            }
        }
    }

    public void runGame() {
        while (!game.isGameCompleted()) {
            System.out.println(game.printGuesses());
            String answer = scanner.nextLine();

            if (answer.length() != 5 || answer.chars().anyMatch(Character::isDigit)) {
                System.out.println("Resposta inválida");
                continue;
            }
            game.addGuessedWord(answer);
        }
        System.out.println(game.printGuesses());
        System.out.println(game.hasWon() ? "Você Venceu" : "Perdeu mané, não amola");
    }

}