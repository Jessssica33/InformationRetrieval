package com.company;

import java.util.DoubleSummaryStatistics;

/**
 * Created by Jessica on 9/25/17.
 */
public class WeightStruct {
    public Integer tf;
    public Double idf;

    public WeightStruct(Integer tf, Double idf) {
        this.tf = tf;
        this.idf = idf;
    }
}
