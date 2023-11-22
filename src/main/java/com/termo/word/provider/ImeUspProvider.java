package com.termo.word.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ImeUspProvider implements TermoProvider {
    private static final String WORDS_UTF8_NO_ACCENT = "https://www.ime.usp.br/~pf/dicios/br-sem-acentos.txt";

    private final List<String> words = new ArrayList<>();

    @Override
    public List<String> getValidWords() {
        if (words.isEmpty()) {
            try {
                words.addAll(requestWords(WORDS_UTF8_NO_ACCENT));
            } catch (IOException e) {
                System.err.println("Erro ao carregar palavras: " + e.getMessage());
            }
        }
        return words;
    }

    private List<String> requestWords(String url) throws IOException {
        List<String> words;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            words = in.lines()
                    .filter(line -> line.length() == 5)
                    .map(line -> line.toUpperCase(Locale.ROOT))
                    .toList();
        }
        return words;
    }
}
