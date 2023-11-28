package com.termo.word;


import java.util.stream.IntStream;

public class TermoWord {
    private static final String GREEN_ANSI_FONT = "\u001b[42;1m";
    private static final String RESET_ANSI_FONT = "\u001b[0m";
    private static final String YELLOW_ANSI_FONT = "\u001b[43;1m";
    private final String word;
    private boolean isCompleted = false;

    public TermoWord(String word) {
        System.out.println(word);
        this.word = word;
    }

    public String checkGuessWord(String guessWord) {
        if (isCompleted) {
            return " _  _  _  _  _ ";
        }

        StringBuilder output = new StringBuilder();

        if (guessWord.equals(word)) {
            isCompleted = true;
            word.chars().forEach(character ->
                    output.append(GREEN_ANSI_FONT)
                            .append(" ")
                            .append((char) character)
                            .append(" ")
                            .append(RESET_ANSI_FONT)
            );
        } else {
            IntStream.range(0, word.length()).forEach(i ->
                    output.append(checkCharacter(guessWord, i))
            );
        }
        return output.toString();
    }

    private String checkCharacter(String guessWord, int index) {
        String guessChar = guessWord.substring(index, index + 1);
        String wordChar = word.substring(index, index + 1);

        if (guessChar.equals(wordChar)) {
            return GREEN_ANSI_FONT + " " + guessChar + " " + RESET_ANSI_FONT;
        } else if (word.contains(guessChar)) {
            return YELLOW_ANSI_FONT + " " + guessChar + " " + RESET_ANSI_FONT;
        } else {
            return RESET_ANSI_FONT + " " + guessChar + " " + RESET_ANSI_FONT;
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}