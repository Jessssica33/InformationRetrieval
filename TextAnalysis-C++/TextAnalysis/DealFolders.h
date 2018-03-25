//
//  DealFolders.h
//  TextAnalysis
//
//  Created by Jessica on 9/2/17.
//  Copyright (c) 2017 Jessica. All rights reserved.
//

#ifndef __TextAnalysis__DealFolders__
#define __TextAnalysis__DealFolders__

#include <stdio.h>
#include <string>
#include <vector>
#include <iostream>

using namespace std;

class DealFolders
{
private :
    string _path;
    vector<string> _files;
    
public:
    
    DealFolders();
    DealFolders(string path);
    
    vector<string> GetAllFiles();
    void Print();
    
};


#endif /* defined(__TextAnalysis__DealFolders__) */
