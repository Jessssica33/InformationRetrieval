package com.company;

import javax.net.ssl.SSLEngineResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.FileReader;
import java.lang.String;

/**
 * Created by Shanshan Wu on 9/4/17.
 */
public class AnalysisFile {

    private boolean IsDocTitleText(String word) {

        if (word.equalsIgnoreCase("<DOC>") || word.equalsIgnoreCase("</DOC>")   ||
                word.equalsIgnoreCase("<TITLE>") || word.equalsIgnoreCase("</TITLE>") ||
                word.equalsIgnoreCase("<TEXT>") || word.equalsIgnoreCase("</TEXT>")) {
            return true;
        }

        return false;
    }

    private String GetFileName(String path) {

        String[] words = path.split("/");

        return words[words.length - 1];
    }

    public Hashtable<String, Hashtable<String, Integer>> CreatHashtable(StopWords sw, String fpath,
                                                                        Hashtable<String, Hashtable<String, Integer>> table) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(fpath));
            String fname = GetFileName(fpath);
            int ignorFlag = 0;
            String line;
            Porter porter = new Porter();

            while ((line = br.readLine()) != null) {

                String[] words = line.split(" ");

                for (int i = 0; i < words.length; ++i) {

                    if (IsDocTitleText(words[i])) {
                        ignorFlag = 0;
                        continue;
                    }

                    String tmp = words[i];

                    if (tmp.isEmpty()) {
                        continue;
                    }
                    if (tmp.charAt(0) == '<' && tmp.charAt(tmp.length() - 1) == '>') {

                        //System.out.println(tmp + tmp.length());
                        ignorFlag = 1;
                        continue;

                    }

                    if (tmp.charAt(0) == '<' && tmp.charAt(1) == '/'
                            && tmp.charAt(tmp.length() - 1) == '>') {

                        //System.out.println(tmp);
                        ignorFlag = 0;
                        continue;

                    }

                    if (ignorFlag == 1) {
                        continue;
                    }

                    String word = porter.stripAffixes(words[i]);
                    if (sw.IsStopWord(word)) {
                        continue;
                    }

                    if (word.isEmpty()) {
                        continue;
                    }

                    Hashtable<String, Integer> inner;
                    if (table.containsKey(word)) {
                        inner = table.get(word);
                        if (inner.containsKey(fname)) {
                            inner.put(fname, inner.get(fname) + 1);
                        } else {
                            inner.put(fname, 1);
                        }

                    } else {
                        inner = new Hashtable<String, Integer>();
                        inner.put(fname, 1);

                        table.put(word, inner);
                    }

                }
            }

        } catch (IOException e) {

            System.out.println(e.toString());
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
                System.out.println(": " + innerName + " " + value);
            }
        }
    }

}
