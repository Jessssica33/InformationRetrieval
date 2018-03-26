package com.company;

/**
 * Created by Shanshan Wu on 9/4/17.
 */

import java.util.ArrayList;
import java.lang.String;
import java.io.File;

public class DealFolder {

    private String path;
    private int count;
    private ArrayList<String> filespath;
    private ArrayList<String> filesname;

    public DealFolder(String p) {

        path = p;
        filespath = new ArrayList<String>();
        filesname = new ArrayList<String>();
    }

    public ArrayList<String> GetAllFiles() {
        String p;
        File dirFile = new File(path);

        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("error path");
            return filespath;
        }

        if (path.charAt(path.length() - 1) == '/') {
            p = path;
        } else {
            p = path + '/';
        }

        String[] fileList = dirFile.list();
        count = fileList.length;

        for (int i = 0; i < fileList.length; ++i) {
            if (fileList[i].charAt(0) != '.') {
                filespath.add(p + fileList[i]);
                filesname.add(fileList[i]);
            }

        }

        return filespath;
    }

    public ArrayList<String> GetAllFilesName() {return filesname;}

    public int GetFileCount() { return count; }

    public void Print() {
        for (int i = 0; i < filespath.size(); ++i) {
            System.out.println(filespath.get(i));

        }
    }
}
