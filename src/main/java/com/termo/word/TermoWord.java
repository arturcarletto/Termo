package com.termo.word;

public class TermoWord {

    private char[] word;

    public TermoWord(String word){
        this.word = word.toCharArray();
    }

    public char[] getWord() {
        return word;
    }

    public char getWordLetter(int index) {
        if (index < 0 || index > 5)
            throw new IllegalArgumentException("msg");
        return word[index];
    }

    public char[] checkGuessWord(String guessWord){
        char[] guess = guessWord.toCharArray();
        for (int i = 0; i < word.length;i++){
            char first = guess[i];
            char second = word[i];

            if (first == second) {
                // verde
            } else if (new String(word).contains(String.valueOf(first))) {
                // amarelo
            } else {
                // vermelho
            }
            
        }
    }

}
