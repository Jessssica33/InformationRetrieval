//
//  main.cpp
//  TextAnalysis
//
//  Created by Jessica on 9/2/17.
//  Copyright (c) 2017 Jessica. All rights reserved.
//

#include <fstream>

#include "DealFolders.h"
#include "DealFile.h"

using namespace std;

void print(unordered_map<string, int> wordMap)
{
    int cnt = 0;
    for (pair<string, int> e : wordMap)
    {
        cnt += e.second;
        cout << e.first << ": " << e.second << '\n' << endl;
    }
    cout << "cnt: " << cnt << '\n' << endl;
}

int main(int argc, const char * argv[])
{

    
    DealFolders df(argv[1]);
    vector<string> files = df.GetAllFiles();
    
    unordered_map<string, int>* wordMap = new unordered_map<string, int>();
    int cnt = 0;
    for (vector<string>::iterator it = files.begin(); it != files.end(); ++it)
    {
        ifstream ifile(argv[1] + *it);
        ofstream ofile("/Users/Jessica/citeseer/" + *it);
        
        //cout << argv[1] + *it << endl;
        DealFile dealFile;
        
        if (ifile.is_open() && ofile.is_open())
        {
            dealFile.RemovePunctuation(ifile, ofile);
            
            ifile.close();
            ofile.close();
        }
        
        
        ifstream rf("/Users/Jessica/citeseer/" + *it);
        dealFile.CreatMap(*wordMap, rf);
        cnt += dealFile.GetAmount();
        rf.close();
    }
    cout << "class total: " << cnt << '\n' << endl;
    print(*wordMap);
    
    return 0;
}
