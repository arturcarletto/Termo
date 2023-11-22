package com.termo;

import java.util.Scanner;

public class Console {
    private final Scanner scanner = new Scanner(System.in);

    public void startGame() {
        while (true) {
            System.out.println("Quantas palavras você vai querer advinhar ao mesmo tempo?");
            try {
                int numberOfWords = Integer.parseInt(scanner.nextLine());
                runGame(new Game(numberOfWords));
            } catch (NumberFormatException nfe) {
                System.out.println("Resposta inválida");
            }
        }
    }

    private void runGame(Game game) {
        while (!game.isGameCompleted()) {
            System.out.println(game.printGuesses());
            String answer = scanner.nextLine().trim();

            if (!isValidAnswer(answer)) {
                System.out.println("Resposta inválida");
                continue;
            }

            if (game.addGuessedWord(answer)) {
                System.out.println("Palavra adicionada");
            } else {
                System.out.println("Palavra inválida, ela não está no dicionário");
            }
        }
        displayGameOutcome(game);
    }

    private boolean isValidAnswer(String answer) {
        return answer.length() == 5 && answer.chars().noneMatch(Character::isDigit);
    }

    private void displayGameOutcome(Game game) {
        System.out.println(game.printGuesses());
        if (game.hasWon()) {
            System.out.println("Você venceu!");
        } else {
            System.out.println("Você perdeu. Tente novamente!");
        }
    }
}