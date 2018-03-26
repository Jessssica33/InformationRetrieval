package com.company;

import jdk.internal.util.xml.impl.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Shanshan Wu on 9/22/17.
 */
public class DealQuery {

    String path;
    ArrayList[] query;

    public DealQuery(String path) {
        this.path = path;
        query = new ArrayList[10];
    }

    public ArrayList[] AnalysisQuery(StopWords sw) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        String[] words;
        int index = 0;
        String word;
        Porter porter = new Porter();
        query[index] = new ArrayList();

        while ((line = br.readLine()) != null) {

            words = line.split(" ");

            for (int i = 0; i < words.length; ++i) {

                word = words[i];
                if (word.charAt(0) == '.') {
                    index++;
                    if (index < 10) {
                        query[index] = new ArrayList();
                    }
                }
                word = porter.stripAffixes(word);
                if (sw.IsStopWord(word)) {
                    continue;
                }

                if (word.isEmpty()) {
                    continue;
                }

                query[index].add(word);

            }

        }

        return query;
    }

    public void Print()
    {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < query[i].size(); ++j) {
                System.out.print(query[i].get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
