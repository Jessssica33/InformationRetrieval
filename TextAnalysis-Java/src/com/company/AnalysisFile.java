package com.company;

import java.io.BufferedReader;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;

/**
 * Created by Shanshan Wu on 9/4/17.
 */
public class AnalysisFile {

    public static boolean DEBUG = false;
    private int total = 0;
    private int vocabularySize = 0;

    private String Clean(String str) {

        int len = str.length();
        String temp = "";
        Character ch;
        for (int i = 0; i < len; ++i) {
            ch = str.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                if (Character.isUpperCase(ch)) {
                    ch = Character.toLowerCase(ch);
                }
                temp += ch;
            }
        }

        return temp;
    }

    public HashMap<String, Integer> CreatHashMap(BufferedReader br, HashMap<String, Integer> hmap, StopWords sw) {

        try {

            String line;
            Porter porter = new Porter();

            while ((line = br.readLine()) != null) {

                String[] words = line.split(" ");

                for (int i = 0; i < words.length; ++i) {

                    String word;
                    if (DEBUG) {
                        //Do not use Porter
                        word = Clean(words[i]);
                    } else {
                        //Use Porter
                        word = porter.stripAffixes(words[i]);
                        if (sw.IsStopWord(word)) {
                            continue;
                        }
                    }

                    if (word.isEmpty()) {
                        continue;
                    }

                    if (hmap.containsKey(word)) {
                        hmap.put(word, hmap.get(word) + 1);
                    } else {
                        hmap.put(word, 1);
                        ++vocabularySize;
                    }

                    ++total;
                }
            }

        } catch (IOException e) {

            System.out.println(e.toString());
        }

        return hmap;

    }

    public TreeMap<String, Integer> SortedHashMap(HashMap<String, Integer> hmap) {

        ValueComparator vc = new ValueComparator(hmap) {

            @Override
            public int compare(String o1, String o2) {
                if (base.get(o1) >= base.get(o2))
                    return -1;
                else
                    return 1;
            }
        };

        TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(vc);

        sortedMap.putAll(hmap);

        return sortedMap;
    }

    public int GetVocabularySize() {
        return vocabularySize;
    }

    public int GetTotalNumber() {
        return total;
    }

    public void Print(HashMap<String, Integer> hmap) {

        //Map<String, Integer> map = new HashMap<String, Integer>();
        for(Map.Entry<String, Integer> entry : hmap.entrySet()){
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }

}
