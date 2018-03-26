package com.company;

import javax.net.ssl.SSLEngineResult;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
/**
 * Created by Jessica on 9/25/17.
 */
public class DealVSRData {

    HashMap<String, Double>[] cosSim;
    Hashtable<String, Hashtable<String, WeightStruct>> doc;

    public DealVSRData() {
        cosSim = new HashMap[10];
        doc = new Hashtable<String, Hashtable<String, WeightStruct>>();

    }

    public TreeMap[] SortAllMap() {

        TreeMap[] tm = new TreeMap[10];
        for (int i = 0; i < cosSim.length; ++i) {

            tm[i] = SortedHashMap(cosSim[i]);
        }

        return tm;
    }

    private TreeMap<String, Double> SortedHashMap(HashMap<String, Double> hmap) {

        ValueComparator vc = new ValueComparator(hmap) {

            @Override
            public int compare(String o1, String o2) {
                if (base.get(o1) >= base.get(o2))
                    return -1;
                else
                    return 1;
            }
        };

        TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);

        sortedMap.putAll(hmap);

        return sortedMap;
    }

    public HashMap<String, Double>[] DealAllQuery(ArrayList[] allQuery, ArrayList<String> dnames,
                                                    Hashtable<String, Hashtable<String, Integer>> table) {

        for (int i = 0; i < allQuery.length; ++i) {

            cosSim[i] = CalCosSim(allQuery[i], dnames, table, i + 1);
        }

        return cosSim;
    }

    private HashMap<String, Double> CalCosSim(ArrayList<String> query, ArrayList<String> dnames,
                                                Hashtable<String, Hashtable<String, Integer>> table, int id) {


        HashMap<String, Double> qCosSim = new HashMap<String, Double>();
        String dname;
        String term;
        WeightStruct ws;
        int count = dnames.size();
        Hashtable<String, Double> collectionWeightSquare = CollectionWeightSquare();
        double docWeightSquare = 0.0;
        double queryWeightSquare = GetQueryWeightSquare(dnames.size(), query, id, table);
        double weight = 0.0;
        double cs = 0.0;


        for (int i = 0; i < dnames.size(); ++i) {

            dname = dnames.get(i);
            if (collectionWeightSquare.containsKey(dname)) {
                docWeightSquare = collectionWeightSquare.get(dname);
            }

            weight = 0.0;
            for (int j = 0; j < query.size(); ++j) {

                term = query.get(j);
                ws = GetQueryIdf(count, term, dname, table);
                weight += ws.tf * ws.idf * ws.idf;
            }

            cs = Math.abs(weight / Math.sqrt(docWeightSquare * queryWeightSquare));
            qCosSim.put(dname, cs);
        }

        return qCosSim;
    }

    private double GetQueryWeightSquare(int count, ArrayList<String> query, int id,
                                        Hashtable<String, Hashtable<String, Integer>> table) {


        double weight = 0.0;
        double idf = 0.0;
        String term;
        Hashtable<String, Integer> inner;
        for (int i = 0; i < query.size(); ++i) {
            term = query.get(i);

            if (table.containsKey(term)) {

                inner = table.get(term);
                idf = Math.log(count/inner.size()) / Math.log(2);
                weight += idf * idf;
            }
        }
        //System.out.println(id + ": " + Math.abs(Math.sqrt(weight)));

        return weight;
    }

    private WeightStruct GetQueryIdf(int count, String term, String dname,
                               Hashtable<String, Hashtable<String, Integer>> table) {


        double idf = 0.0;
        Integer tf = 0;
        if (table.containsKey(term)) {

            Hashtable<String, Integer> inner = table.get(term);
            idf = Math.log(count/inner.size())/Math.log(2);
            if (inner.containsKey(dname)) {
                tf = inner.get(dname);
            }
        }
        WeightStruct ws = new WeightStruct(tf, idf);

        return ws;
    }


    //first call
    public Hashtable<String, Hashtable<String, WeightStruct>> CollectionTFIDF(int count,
                                                                              Hashtable<String, Hashtable<String, Integer>> table) {

        Enumeration terms = table.keys();
        String term;
        int df = 0;    //document frequency
        Hashtable<String, Integer> inner;

        while (terms.hasMoreElements()) {

            term = (String)terms.nextElement();
            inner = table.get(term);

            df = inner.size();

            Enumeration dnames = inner.keys();
            String dname;
            int tf;
            Double idf;
            while (dnames.hasMoreElements()) {

                dname = (String) dnames.nextElement();
                tf = inner.get(dname);

                idf = Math.log(count/df) / Math.log(2);
                Hashtable<String, WeightStruct> doctfidf;
                if (doc.containsKey(dname)) {
                    doctfidf = doc.get(dname);
                    doctfidf.put(term, new WeightStruct(tf, idf));
                } else {
                    doctfidf = new Hashtable<String, WeightStruct>();
                    doctfidf.put(term, new WeightStruct(tf, idf));
                    doc.put(dname, doctfidf);
                }
            }
        }

        return doc;
    }


    private Hashtable<String, Double> CollectionWeightSquare() {

        Hashtable<String, Double> dws = new Hashtable<String, Double>();

        Enumeration dnames = doc.keys();
        String dname;
        while (dnames.hasMoreElements()) {

            dname = (String) dnames.nextElement();

            Hashtable<String, WeightStruct> termWeight = doc.get(dname);
            Enumeration terms = termWeight.keys();
            String term;
            Double weightSquare = 0.0;
            while (terms.hasMoreElements()) {
                term = (String) terms.nextElement();

                WeightStruct ws = termWeight.get(term);
                weightSquare += Math.pow(ws.tf * ws.idf, 2);
            }
            dws.put(dname, weightSquare);
            //System.out.println(dname + ": " + Math.abs(Math.sqrt(weightSquare)));

        }

        return dws;
    }

    public void Print(TreeMap[] tm) {

        for (int i = 0; i < tm.length; ++i) {
            TreeMap<String, Double> treeMap = tm[i];
            for (Map.Entry<String, Double> entry : treeMap.entrySet()) {
                System.out.println(i + ": " + entry.getKey() + "  " + entry.getValue());
            }
        }
    }
}
