package com.termo;

import com.termo.word.TermoWord;
import com.termo.word.provider.ImeUspProvider;
import com.termo.word.provider.TermoProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private final int tries;

    private final TermoProvider wordProvider = new ImeUspProvider();

    private final TermoWord[] termoWords;

    private final List<String> guessedWords = new ArrayList<>();

    public Game(int wordCount) {
        tries = 4 + wordCount;
        termoWords = new TermoWord[wordCount];
        for (int i = 0; i < wordCount; i++) {
            termoWords[i] = new TermoWord(wordProvider.provideRandomMessage());
        }
    }

    public String printGuesses() {
        return IntStream.range(0, tries)
                .mapToObj(this::createLine)
                .collect(Collectors.joining("\n"));
    }

    private String createLine(int lineIndex) {
        return IntStream.range(0, termoWords.length)
                .mapToObj(i -> getGuessOrPlaceholder(i, lineIndex))
                .collect(Collectors.joining(" | "));
    }

    private String getGuessOrPlaceholder(int wordIndex, int lineIndex) {
        int guessIndex = wordIndex + (lineIndex * termoWords.length);
        return guessIndex < guessedWords.size() ? guessedWords.get(guessIndex) : " _  _  _  _  _ ";
    }

    public boolean isGameCompleted() {
        return hasWon() || (guessedWords.size() / termoWords.length) >= tries;
    }

    public boolean hasWon() {
        return Arrays.stream(termoWords).allMatch(TermoWord::isCompleted);
    }

    public boolean addGuessedWord(String guessedWord) {
        if (!wordProvider.isAValidWord(guessedWord)) {
            return false;
        }
        Arrays.stream(termoWords).forEach(termoWord ->
                guessedWords.add(termoWord.checkGuessWord(guessedWord.toUpperCase(Locale.ROOT)))
        );
        return true;
    }
}