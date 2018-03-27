package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jessica on 12/10/17.
 */
public class URLSet {

    public String CreateUrlSet(HashSet<String> urlSet, Queue<String> urlQueue,String buf, String currentUrl) {

        ArrayList<String> urlList = extractUrl(buf, currentUrl);

        for (int i = 0; i < urlList.size(); ++i) {
            String link = urlList.get(i);

            if (!urlSet.contains(link)) {
                if (link.contains("ksu.edu") || link.contains("k-state.edu")) {
                    if (urlSet.size() <= 3000) {
                        urlSet.add(link);
                        urlQueue.add(link);


                    }
                }
            }

        }

        String content = HTMLStrip.delHTMLTag(buf);
        content = HTMLStrip.removeHTMLInString(content);
        return content;
    }


    private ArrayList<String> extractUrl(String str, String url) {
        ArrayList<String> urlList = new ArrayList<>();

        String regex ="<a.*?/a>";
        Pattern pt=Pattern.compile(regex);
        Matcher m=pt.matcher(str);
        while(m.find()) {
            Matcher myurl=Pattern.compile("href=[\\\"']?((https?://)?/?[^\\\"']+)[\\\"']?.*?>").matcher(m.group());
            while(myurl.find())
            {
                String foundURL = myurl.group(1);

                if (foundURL.endsWith("/")) {
                    StringBuilder sb = new StringBuilder(foundURL);
                    sb.deleteCharAt(foundURL.length() - 1);
                    foundURL = sb.toString();
                }

                //System.out.println(foundURL);
                if (foundURL.endsWith("pdf")) {
                    continue;
                } else if (foundURL.startsWith("http")) {

                    urlList.add(foundURL);

                }  else if (foundURL.startsWith("//")){
                    urlList.add("http:" + foundURL);


                } else {
                    if (foundURL.startsWith("/")) {
                        urlList.add(url + foundURL);
                    }
                }

            }
        }
        return urlList;
    }

}
