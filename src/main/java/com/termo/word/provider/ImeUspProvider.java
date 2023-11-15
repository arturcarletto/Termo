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
            try {
                validWords.addAll(requestWords());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return validWords;
    }

    private List<String> requestWords() throws IOException {
        URL website = new URL(ImeUspProvider.WORDS_UTF8);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;

        List<String> words = new ArrayList<>();
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.length() == 5)
                words.add(inputLine);
        }

        in.close();
        return words;
    }

}
