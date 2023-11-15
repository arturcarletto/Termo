package com.termo.word.provider;

import java.security.SecureRandom;
import java.util.List;

public interface TermoProvider {

    SecureRandom RANDOM = new SecureRandom();

    default String provideRandomMessage() {
        return getValidWords().get(RANDOM.nextInt(getValidWords().size()));
    }

    List<String> getValidWords();

}
