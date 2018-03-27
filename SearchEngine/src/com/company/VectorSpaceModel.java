package com.company;

/**
 * Created by Jessica on 12/5/17.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Enumeration;

public class VectorSpaceModel {

    private StopWords sw;

    public VectorSpaceModel(){

        sw = new StopWords();
        sw.StopWordsHash();
    }

    public  Hashtable<String, Hashtable<String, Integer>> CreateVectorSpace(String url, String buf,
                                                                           Hashtable<String, Hashtable<String, Integer>> table)
    {
        Porter porter = new Porter();

        String[] words = buf.split(" ");
        for (int i = 0; i < words.length; ++i) {

            String word = porter.stripAffixes(words[i]);
            if (word.length() > 10) {
                continue;
            }
            if (sw.IsStopWord(word)) {
                continue;
            }

            if (word.isEmpty()) {
                continue;
            }

            synchronized (this) {
                Hashtable<String, Integer> inner;
                if (table.containsKey(word)) {
                    inner = table.get(word);
                    if (inner.containsKey(url)) {
                        inner.put(url, inner.get(url) + 1);
                    } else {
                        inner.put(url, 1);
                    }
                } else {
                    inner = new Hashtable<String, Integer>();
                    inner.put(url, 1);
                    table.put(word, inner);

                    //System.out.println(word + " ----- " + url);
                }
            }

        }

        return table;
    }

    public void Print(Hashtable<String, Hashtable<String, Integer>> table) {

        Enumeration keys = table.keys();
        String name;
        Enumeration innerKeys;
        String innerName;
        Integer value;
        Hashtable<String, Integer> inner;
        while (keys.hasMoreElements()) {
            name = (String)keys.nextElement();
            inner = table.get(name);

            innerKeys = inner.keys();
            System.out.print(name);
            while (innerKeys.hasMoreElements()) {
                innerName = (String) innerKeys.nextElement();
                value = inner.get(innerName);
                System.out.println(" -- " + innerName + " " + value);
            }
        }
    }

}
