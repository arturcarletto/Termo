package com.termo.word.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ImeUspProvider implements TermoProvider {
    private static final String WORDS_UTF8 = "https://www.ime.usp.br/~pf/dicios/br-utf8.txt";
    private final List<String> validWords = new ArrayList<>();

    @Override
    public List<String> getValidWords() {
        if (validWords.isEmpty()) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(WORDS_UTF8).openStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.length() == 5) validWords.add(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return validWords;
    }
}
