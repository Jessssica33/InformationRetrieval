package com.company;

/**
 * Created by Shanshan Wu on 9/4/17.
 */

import java.util.ArrayList;
import java.lang.String;
import java.io.File;

public class DealFolder {

    private String path;
    private ArrayList<String> files;

    public DealFolder(String p) {
        path = p;
        files = new ArrayList<String>();
    }

    public ArrayList<String> GetAllFiles() {
        String p;
        File dirFile = new File(path);

        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("error path");
            return files;
        }

        if (path.charAt(path.length() - 1) == '/') {
            p = path;
        } else {
            p = path + '/';
        }

        String[] fileList = dirFile.list();

        for (int i = 0; i < fileList.length; ++i) {
            if (fileList[i].charAt(0) != '.') {
                files.add(p + fileList[i]);
            }

        }

        return files;
    }

    public void Print() {
        for (int i = 0; i < files.size(); ++i) {
            System.out.println(files.get(i));

        }
    }
}
