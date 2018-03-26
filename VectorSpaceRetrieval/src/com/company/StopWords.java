package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.BufferedReader;

/**
 * Created by Jessica on 9/6/17.
 */
public class StopWords {

    private HashMap<String, Integer> hmap;

    public StopWords() {

        hmap = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> StopWordsHash(BufferedReader br) {

        try {
            String line;

            while ((line = br.readLine()) != null) {

                hmap.put(line, 1);
            }

        } catch (IOException e) {

            System.out.println(e.toString());
        }

        return hmap;
    }

    public boolean IsStopWord(String word) {

        return hmap.containsKey(word);
    }
}
