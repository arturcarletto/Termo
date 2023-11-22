package com.termo;

import com.termo.word.TermoWord;
import com.termo.word.provider.ImeUspProvider;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int tries;
    private final List<TermoWord> termoWords = new ArrayList<>();
    private final List<String> guessedWords = new ArrayList<>();

    public Game(int wordCount) {
        ImeUspProvider imeUspProvider = new ImeUspProvider();
        tries = 4 + wordCount;
        for (int i = 0; i < wordCount; i++) {
            termoWords.add(new TermoWord(imeUspProvider.provideRandomMessage()));
        }
    }

    public String printGuesses() {
        StringBuilder finalLine = new StringBuilder();
        for (int i = 0; i < tries; i++) {
            List<String> line = new ArrayList<>();
            if(guessedWords.size() > i * termoWords.size()){
                for(int j = 0; j < termoWords.size();j++){
                    line.add(guessedWords.get(j + (i * termoWords.size())));
                }
            }else{
                for(int j = 0; j < termoWords.size();j++){
                    line.add(" _  _  _  _  _ ");
                }
            }
            finalLine.append(String.join(" | ", line)).append("\n");
        }
        return finalLine.toString();
    }

    public boolean isGameCompleted() {
        return hasWon() || (guessedWords.size() / termoWords.size()) >= tries;
    }

    public boolean hasWon() {
        return termoWords.stream().allMatch(TermoWord::isCompleted);
    }

    public void addGuessedWord(String guessedWord){
        for(TermoWord termoWord: termoWords){
            guessedWords.add(termoWord.checkGuessWord(guessedWord.toUpperCase()));
        }
    }

}