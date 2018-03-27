package com.company;

/**
 * Created by Jessica on 11/13/17.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class StopWords {

    private static HashMap<String, Integer> hmap;

    public StopWords() {

        hmap = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> StopWordsHash() {

        try {

            BufferedReader br = new BufferedReader(new FileReader("./stopwords.txt"));
            //StopWords sw = new StopWords();
            //sw.StopWordsHash(br);

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
