package com.company;

/* Create by Shanshan Wu on 9/4/17 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.Debug;

public class Main {

    private static HashMap<String, Integer> swHash;

    private static void printAnswer(AnalysisFile af, TreeMap<String, Integer> sortedMap, StopWords sw) {

        System.out.println("Qa. The total number of words: " + af.GetTotalNumber());
        System.out.println("Qb. The vocabulary size: " + af.GetVocabularySize());

        int cnt = 0;

        ArrayList<Pair<String, Integer>> pairList = new ArrayList<Pair<String, Integer>>();

        System.out.println("Qc. The top 20 words in the ranking: ");
        for(Map.Entry<String, Integer> entry : sortedMap.entrySet()){
            String word = entry.getKey();
            pairList.add(new Pair<String, Integer>(word, entry.getValue()));
            System.out.println("key = " + word + ", value = " + entry.getValue());

            if (AnalysisFile.DEBUG) {
                if (!sw.IsStopWord(word)) {
                    System.out.println("Qd. " + word + " is not stopword");
                }
            }

            ++cnt;
            if (AnalysisFile.DEBUG) {
                if (cnt == 20) {
                    break;
                }
            } else {
                if (cnt == 25) {
                    break;
                }
            }
        }

        System.out.println("Qe. The minimum number of words accounting for 15% is: "
                + calculateMini(af.GetTotalNumber(), pairList, 0.15));
    }

    private static void dealMap(ArrayList<String> files, StopWords sw) throws IOException {

        HashMap<String, Integer> hmap = new HashMap<String, Integer>();

        AnalysisFile af = new AnalysisFile();

        for (int i = 0; i < files.size(); ++i) {

            BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
            hmap = af.CreatHashMap(br, hmap, sw);
        }

        TreeMap<String, Integer> sortedMap = af.SortedHashMap(hmap);

        printAnswer(af, sortedMap, sw);

    }

    private static int calculateMini(int total, ArrayList<Pair<String, Integer>> pairList, double percent) {

        double mini = total * percent;
        double count = 0.0;
        int i = 0;
        for (; i < pairList.size(); ++i) {

            count += pairList.get(i).getValue();

            if (count >= mini) {
                break;
            }
        }

        return i + 1;
    }

    public static void main(String[] args) throws IOException {

        //String path = "/Users/Jessica/Desktop/citeseer";
        String path = args[0];

        DealFolder dFolder = new DealFolder(path);
        ArrayList<String> files = dFolder.GetAllFiles();

        BufferedReader br = new BufferedReader(new FileReader("./stopwords.txt"));
        StopWords sw = new StopWords();
        swHash = sw.StopWordsHash(br);

        dealMap(files, sw);
    }
}
