package com.company;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Jessica on 9/4/17.
 */
public abstract class ValueComparator implements Comparator<String> {

    Map<String, Double> base;

    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    /*public int comapre(String a, String b) {

        if (base.get(a) >= base.get(b))
            return 1;
        else
            return -1;

    }*/
}
