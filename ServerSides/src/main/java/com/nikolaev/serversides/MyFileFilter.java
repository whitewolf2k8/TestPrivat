package com.nikolaev.serversides;

import java.io.File;
import java.io.FileFilter;
 
class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) 
    {
       return pathname.isFile() && pathname.getName().endsWith(".txt");
    }
}