package ru.testexersize.dirscan;



import ru.testexersize.dirscan.utils.CommandLineOptions;
import ru.testexersize.dirscan.utils.Prefix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.MatchResult;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 0){
            System.out.println("Params not specified. Usage:");
            System.out.println("dirscan foldertosearch - folderToExclude -F filesToExclude");
            return;
        }

        CommandLineOptions commandLineOptions = new CommandLineOptions(args);

        Map<String, List<String>> filesAndFolders = commandLineOptions.parseCommand();

        ScanDirectories sd = new ScanDirectories(filesAndFolders);

        for(String s: filesAndFolders.get("s")){
            Path walkDir = Paths.get(s);
            Files.walkFileTree(walkDir,sd);
        }

    }
}