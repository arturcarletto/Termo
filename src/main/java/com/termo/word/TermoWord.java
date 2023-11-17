package com.termo.word;

import java.util.ArrayList;
import java.util.List;

public class TermoWord {
    private final String GREEN_ANSI_FONT = "\u001b[42;1m";
    private final String YELLOW_ANSI_FONT = "\u001b[43;1m";
    private final String RESET_ANSI_FONT = "\u001b[0m";
    private final String word;
    private boolean isCompleted = false;

    public TermoWord(String word) {
        this.word = word;
    }

    public String checkGuessWord(String guessWord) {
        if (isCompleted) {
            return " _  _  _  _  _ ";
        }

        StringBuilder output = new StringBuilder();

        if (guessWord.equals(word)) {
            isCompleted = true;
            for (char character : word.toCharArray()) {
                output.append(GREEN_ANSI_FONT).append(" ").append(character).append(" ").append(RESET_ANSI_FONT);
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                String first = guessWord.substring(i, i + 1);
                String second = word.substring(i, i + 1);

                if (first.equals(second)) {
                    output.append(GREEN_ANSI_FONT).append(" ").append(first).append(" ").append(RESET_ANSI_FONT);
                } else if (word.contains(first)) {
                    output.append(YELLOW_ANSI_FONT).append(" ").append(first).append(" ").append(RESET_ANSI_FONT);
                } else {
                    output.append(RESET_ANSI_FONT).append(" ").append(first).append(" ").append(RESET_ANSI_FONT);
                }
            }
        }
        return output.toString();
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
