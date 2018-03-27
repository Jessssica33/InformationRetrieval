package com.company;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void print(TreeMap<String, Double> treeMap, int n) {

        int count = 0;
        String last = treeMap.firstKey();

        System.out.println("Top five results: ");
        for (Map.Entry<String, Double> entry : treeMap.entrySet()) {

            if (count == n) {
                break;
            }
            String current = entry.getKey();
            Double value = entry.getValue();
            if (current.endsWith("index.html") || last.endsWith("index.html")) {

                if (current.equalsIgnoreCase(last + "/index.html") ||
                        last.equalsIgnoreCase(current + "/index.html")) {
                    continue;
                }
            }

            System.out.println(current + "-------" + value);
            ++count;
            last = current;
        }

    }

    public static void main(String[] args) throws Exception{

        HashSet<String> urlSet = new HashSet<String>();
        Queue<String> urlQueue = new LinkedList<String>();
        //HashSet<String> exUrlSet = new HashSet<String>();
        Hashtable<String, Hashtable<String, Integer>> table = new Hashtable<>();

        VectorSpaceModel vsm = new VectorSpaceModel();

        StopWords sw = new StopWords();
        sw.StopWordsHash();

        String headUrl = "http://cs.ksu.edu";
        urlSet.add(headUrl);
        urlQueue.add(headUrl);

        System.out.print("Please input your query: ");
        Scanner scan = new Scanner(System.in);

        ReadWebPage r = new ReadWebPage();
        URLSet uSet = new URLSet();

        int count = 0;
        String content = "";

        /*ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> list = new LinkedList<Future<String>>();
        SearchEngineCallable callable = new SearchEngineCallable();*/

        while (!urlQueue.isEmpty()) {

            String currentUrl = urlQueue.remove();

            try {
                content = r.read(currentUrl);
                //callable.acceptArguments(currentUrl, r);
                //Future<String> future = executor.submit(callable);
                //list.add(future);

            } catch (Exception e){
                r = new ReadWebPage();
                continue;
            }

            //for (Future<String> fut: list) {

                //content = fut.get();

                if (content != null) {
                    content = uSet.CreateUrlSet(urlSet, urlQueue, content, currentUrl);
                    vsm.CreateVectorSpace(currentUrl, content, table);
                }
            //}

            count++;
            if (count == 3000) {
                break;
            }
        }

        //vsm.Print(table);
        //executor.shutdown();



        Porter porter = new Porter();
        ArrayList<String> querys = new ArrayList<>();
        String inputs = "";
        String query;
        if (scan.hasNextLine()) {
            inputs = scan.nextLine();
            String[] input = inputs.split(" ");
            for (int i = 0; i < input.length; ++i) {
                if (!sw.IsStopWord(input[i])) {
                    query = porter.stripAffixes(input[i]);
                    querys.add(query);
                }
            }
        }

        DealVSRData dvsr = new DealVSRData();
        dvsr.CollectionTFIDF(count, table);

        PrintWriter writer = new PrintWriter("./urlSet.txt", "UTF-8");
        String[] urlList = urlSet.toArray(new String[urlSet.size()]);
        for (int i = 0; i < urlList.length; ++i) {
            writer.println(urlList[i]);
        }
        writer.close();

        HashMap<String, Double> queryCosSim = dvsr.CalCosSim(querys, urlList, table, 0);
        TreeMap<String, Double> sortedMap = dvsr.SortedHashMap(queryCosSim);

        print(sortedMap, 5);


    }
}
