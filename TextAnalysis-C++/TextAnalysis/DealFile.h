//
//  DealFile.h
//  TextAnalysis
//
//  Created by Jessica on 9/2/17.
//  Copyright (c) 2017 Jessica. All rights reserved.
//

#ifndef __TextAnalysis__DealFile__
#define __TextAnalysis__DealFile__

#include <stdio.h>
#include <string>
#include <unordered_map>
#include <iostream>

using namespace std;


class DealFile {

private:
    
    //string _fileName;
    int _total;
    
public:
    
    DealFile();
    void RemovePunctuation(ifstream& oldFile, ofstream& newFile);
    unordered_map<string, int> CreatMap(unordered_map<string, int>& umap, ifstream& ifile);
    int GetAmount();
    void Print(ifstream& rf);
    

};

#endif /* defined(__TextAnalysis__DealFile__) */


