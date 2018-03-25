//
//  DealFolders.cpp
//  TextAnalysis
//
//  Created by Jessica on 9/2/17.
//  Copyright (c) 2017 Jessica. All rights reserved.
//

#include "DealFolders.h"
#include <dirent.h>

DealFolders::DealFolders(){}

DealFolders::DealFolders(string path)
{
    _path = path;
}

vector<string> DealFolders::GetAllFiles()
{
    struct dirent * ptr;
    DIR* dir;
    
    dir = opendir(_path.c_str());
    while ((ptr = readdir(dir)) != NULL)
    {
        if (ptr->d_name[0] == '.')
            continue;
        _files.push_back(ptr->d_name);
    }
    
    return _files;
}

void DealFolders::Print()
{
    for (vector<string>::iterator it = _files.begin(); it != _files.end(); ++it)
    {
        cout << ' ' << *it;
        cout << '\n';
    }

}

