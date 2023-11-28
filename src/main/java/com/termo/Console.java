package com.termo;

import com.termo.connection.implementation.SQLiteConnection;
import com.termo.game.Game;
import com.termo.game.GameStatistics;
import com.termo.game.GameStatisticsDAO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Console {
    private final Scanner scanner = new Scanner(System.in);

    private final GameStatisticsDAO gameStatisticsDAO;

    public Console() {

        var sqliteConnection = new SQLiteConnection();

        gameStatisticsDAO = new GameStatisticsDAO(sqliteConnection);
    }

    public void startOptions() {
        while (true) {

            System.out.println("""
                Bem vindo ao jogo do termo!
                Escolha uma opção:
                1 - Jogar
                2 - Ver estatísticas
                """);

            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> startGame();
                    case 2 -> {
                        System.out.println("""
                            Escolha o tipo de jogo (em quantidade de palavras):
                            """);
                        try {
                            int size = Integer.parseInt(scanner.nextLine());
                            displayUserGameStatistics(size);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Opção inválida");
                        }
                    }
                    default -> System.out.println("Opção inválida");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Opção inválida");
            }
        }

    }


    public void startGame() {
        while (true) {
            System.out.println("""
                    Quantas palavras você vai querer advinhar ao mesmo tempo?
                    """);
            try {
                int numberOfWords = Integer.parseInt(scanner.nextLine());
                runGame(new Game(numberOfWords));
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Quantidade de palavras inválida");
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

            if (!game.addGuessedWord(answer)) {
                System.out.println("Palavra inválida, ela não está no dicionário");
            }
        }

        gameStatisticsDAO.insert(game.dumpStatistics());

        displayGameOutcome(game);
        startOptions();
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

    private void displayUserGameStatistics(int size) {
        List<GameStatistics> filteredStats = gameStatisticsDAO.getAll()
                .stream()
                .filter(s -> s.size() == size)
                .collect(Collectors.toList());

        if (filteredStats.isEmpty()) {
            System.out.println("Nenhuma estatística encontrada");
            return;
        }

        int totalGames = filteredStats.size();
        int totalWins = (int) filteredStats.stream().filter(GameStatistics::hasWon).count();
        double winPercentage = totalWins * 100.0 / totalGames;
        if (Double.isNaN(winPercentage)) {
            winPercentage = 0;
        }

        int longestWinStreak = 0;
        int currentWinStreak = 0;

        for (GameStatistics gameStatistics : filteredStats) {
            System.out.println(gameStatistics);
            if (gameStatistics.hasWon()) {
                currentWinStreak++;
                longestWinStreak = Math.max(longestWinStreak, currentWinStreak);
            } else {
                currentWinStreak = 0;
            }
        }


        System.out.println("Estatísticas do Jogo:");
        System.out.println("Porcentagem de Vitória: " + winPercentage + "%");
        System.out.println("Quantidade de Vitórias: " + totalWins);
        System.out.println("Maior Sequência de Vitórias: " + longestWinStreak);
        System.out.println("Sequência Atual de Vitórias: " + currentWinStreak);

        displayAverageAttemptsTable(filteredStats);
    }

    private void displayAverageAttemptsTable(List<GameStatistics> statisticsList) {
        System.out.println("\nTabela de Média de Tentativas:");
        System.out.println("Tentativas | Quantidade de Jogos");
        System.out.println("-----------+--------------------");

        statisticsList.stream()
                .filter(GameStatistics::hasWon)
                .collect(Collectors.groupingBy(GameStatistics::tries, Collectors.counting()))
                .forEach((tries, count) -> System.out.printf("%-10d | %-10d\n", tries, count));
    }

}