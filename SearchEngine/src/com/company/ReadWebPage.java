package com.company;

/**
 * Created by Jessica on 11/13/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;

public class ReadWebPage {

    //private String url;



    static public String read(String url) throws Exception{

        URL urlObj = new URL(url);
        StringBuilder pagebuf = new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection)urlObj.openConnection();

        connection.setRequestMethod("GET");
        //connection.setConnectTimeout(2000);
        //connection.setReadTimeout(2000);


        if (connection.getResponseCode() == 200) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));

            String line, link;

            while ((line = reader.readLine()) != null) {

                pagebuf.append(line);
                pagebuf.append("\n");

            }
            return pagebuf.toString();
        } else {
            //System.out.println("cannot create connection");
            //System.out.println(url);
            return null;
        }

    }
}
