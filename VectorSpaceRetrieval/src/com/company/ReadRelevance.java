package com.company;

/**
 * Created by Jessica on 9/25/17.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadRelevance {

    String path;
    ArrayList[] reFile;

    public ReadRelevance(String path) {
        this.path = path;
        reFile = new ArrayList[10];
    }


    public ArrayList[] AnalysisRelevance() throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        String words[];
        String word;
        int index = 0;
        int flag = 0;
        while ((line = br.readLine()) != null) {

            words = line.split(" ");
            index = Integer.parseInt(words[0]);
            if (index != flag) {
                reFile[index - 1] = new ArrayList();
            }

            reFile[index - 1].add(Integer.parseInt(words[1]));
            flag = index;
        }

        return reFile;
    }

    public void Print() {

        for (int i = 0; i < reFile.length; ++i) {
            ArrayList<Integer> re = reFile[i];
            for (int j = 0; j < re.size(); ++j) {
                System.out.println((i + 1) + ": " + re.get(j));
            }
        }
    }


}
