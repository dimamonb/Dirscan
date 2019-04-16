package ru.testexersize.dirscan.utils;

import ru.testexersize.dirscan.ScanDirectories;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DirScanPorcessor implements Callable<String> {
    private Path walkDir;
    private Map<String, List<String>> filesAndFolders;


    public DirScanPorcessor(Path walkDir, Map<String, List<String>> filesAndFolders) {
        this.walkDir = walkDir;
        this.filesAndFolders = filesAndFolders;
    }

    @Override
    public String call() throws Exception {
        ScanDirectories sd = new ScanDirectories(filesAndFolders);
        Files.walkFileTree(walkDir,sd);
        return "Finised";
    }
}
