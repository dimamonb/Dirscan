package ru.testexersize.dirscan.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

public class FileDirFilter implements FileFilter {
    private Map<String, List<String>> excludeFilesAndFolders;

    public FileDirFilter(Map<String, List<String>> excludeFilesAndFolders) {
        this.excludeFilesAndFolders = excludeFilesAndFolders;
    }

    @Override
    public boolean accept(File pathname) {
        if(excludeFilesAndFolders.get("-f").contains(pathname.getName())){
            return true;
        }
        return false;
    }
}
