package com.company;

import javax.net.ssl.SSLEngineResult;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Main {

    public static void print(TreeMap[] tm, int n) {

        int count = 0;
        for (int i = 0; i < tm.length; ++i) {

            TreeMap<String, Double> treeMap = tm[i];
            count = 0;
            for (Map.Entry<String, Double> entry : treeMap.entrySet()) {

                if (count == n) {
                    break;
                }
                System.out.println((i + 1) + ": " + entry.getKey() + "  " + entry.getValue());
                ++count;
            }
        }
    }

    private static int getNumberOfName(String dname) {

        String sub = dname.substring(9);
        //System.out.print(sub);

        return Integer.parseInt(sub);
    }

    public static ValueHolder calAveragePrecisionAndRecall(ArrayList[] rel, TreeMap[] tm, int n) {

        //System.out.println(rel.length + " " + tm.length);
        if (rel.length != tm.length) {
            return null;
        }

        int size = rel.length;
        float totalPre = 0;
        float totalRec = 0;
        ValueHolder vh;
        for (int i = 0; i < size; ++i) {

            vh = calPrecisionAndRecall(rel[i], tm[i], n);
            totalPre += vh.precision;
            totalRec += vh.recall;
        }

        return new ValueHolder(totalPre/10, totalRec/10);
    }

    private static ValueHolder calPrecisionAndRecall(ArrayList<Integer> rel, TreeMap<String, Double> tm, int n) {

        int validCount = 0;
        int count = 0;
        int docId = 0;
        float precision = 0;
        float recall = 0;
        for (Map.Entry<String, Double> entry: tm.entrySet() ) {

            if (count == n) {
                break;
            }

            docId = getNumberOfName(entry.getKey());
            if (rel.contains(docId)) {
                validCount++;
            }

            ++count;
        }

        precision = (float)validCount / n;
        recall = (float)validCount / rel.size();


        return new ValueHolder(precision, recall);
    }


    public static void main(String[] args) throws IOException{

        //String path = "/Users/Jessica/Desktop/cranfieldDocs";
        String path = args[0];
        DealFolder df = new DealFolder(path);
        ArrayList<String> files = df.GetAllFiles();
        ArrayList<String> filesname = df.GetAllFilesName();
        int n = Integer.parseInt(args[1]);

        BufferedReader br = new BufferedReader(new FileReader("./stopwords.txt"));
        StopWords sw = new StopWords();
        sw.StopWordsHash(br);

        Hashtable<String, Hashtable<String, Integer>> table = new Hashtable<String, Hashtable<String, Integer>>();

        AnalysisFile af = new AnalysisFile();

        for (int i = 0; i < files.size(); ++i) {

            table = af.CreatHashtable(sw, files.get(i), table);

        }


        DealQuery dq = new DealQuery("./queries.txt");
        ArrayList[] allQuery = dq.AnalysisQuery(sw);


        DealVSRData dvsr = new DealVSRData();
        dvsr.CollectionTFIDF(filesname.size(), table);
        HashMap[] queryCosSim = dvsr.DealAllQuery(allQuery, filesname, table);
        TreeMap[] tMap = dvsr.SortAllMap();
        //dvsr.Print(tMap);
        print(tMap, n);
        ReadRelevance rr = new ReadRelevance("./relevance.txt");
        ArrayList[] relevance = rr.AnalysisRelevance();
        //rr.Print();

        ValueHolder vh = calAveragePrecisionAndRecall(relevance, tMap, n);
        System.out.println("Top " + n + " range: " + "Precision: " + vh.precision + " Recall: " + vh.recall);

    }
}
