package com.company;

/**
 * Created by Jessica on 12/12/17.
 */

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;

public class SearchEngineCallable implements Callable<String> {

    private ReadWebPage r;
    private String url;



    public void acceptArguments(String url, ReadWebPage r) {
                                /*VectorSpaceModel vsm,
                                HashSet<String> urlSet, Queue<String> urlQueue,
                                Hashtable<String, Hashtable<String, Integer>> table) {*/

        this.url = url;
        this.r = r;
        //this.uSet = uSet;
        /*this.vsm = vsm;
        this.urlSet = urlSet;
        this.urlQueue = urlQueue;
        this.table = table;*/
    }


    @Override
    public String call() throws Exception{

        String content = r.read(url);

        return content;
    }
}
