//
//  DealFile.cpp
//  TextAnalysis
//
//  Created by Jessica on 9/2/17.
//  Copyright (c) 2017 Jessica. All rights reserved.
//

#include <fstream>
#include "DealFile.h"

DealFile::DealFile()
{
    _total = 0;
}

void DealFile::RemovePunctuation(ifstream& oldFile, ofstream& newFile)
{
    //ifstream oldFile(_fileName);          //read file
    //ofstream newFile(_fileName + "new");  //write file
    
    char data[256] = {};
    char tmp[256] = {};
    
    while (!oldFile.eof())
    {
        memset(data, 0, 256);
        memset(tmp, 0, 256);
        
        oldFile.read(data, 255);
        
        for (int i = 0; i < strlen(data); ++i)
        {
            
            if ((data[i] >= 'A' && data[i] <= 'Z') ||
                (data[i] >= 'a' && data[i] <= 'z') ||
                (data[i] >= '0' && data[i] <= '9') ||
                (data[i] == ' ') ||
                (data[i] == '-'))
            {
                tmp[i] = data[i];
                
            }
            else
            {
                tmp[i] = ' ';
            }
            
        }
        
        newFile.write(tmp, strlen(tmp));
        
    }

}

unordered_map<string, int> DealFile::CreatMap(unordered_map<string, int>& wordMap, ifstream& ifile)
{
    string word;
    
    while (ifile >> word)
    {
        
        auto search = wordMap.find(word);
        if (search != wordMap.end())
        {
            search->second += 1;
        }
        else
        {
            wordMap.insert({word, 1});
        }
        _total += 1;
            
    }
    
    return wordMap;
}

void DealFile::Print(ifstream& rf)
{
    string word;
    int i = 0;
    while (rf >> word)
    {
        cout << word << ' ' << endl;
        ++i;
        
        
    }
    cout << '\n' << i << endl;

}

int DealFile::GetAmount()
{
    return _total;
}