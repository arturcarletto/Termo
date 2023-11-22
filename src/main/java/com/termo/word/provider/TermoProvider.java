package com.termo.word.provider;

import java.security.SecureRandom;
import java.util.List;

public interface TermoProvider {
    SecureRandom RANDOM = new SecureRandom();

    default String provideRandomMessage() {
        List<String> words = getValidWords();
        return words.get(RANDOM.nextInt(words.size()));
    }

    default boolean isAValidWord(String word) {
        return getValidWords().contains(word);
    }

    List<String> getValidWords();

}
