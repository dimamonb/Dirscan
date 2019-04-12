package ru.testexersize.dirscan;



import ru.testexersize.dirscan.utils.CommandLineOptions;
import ru.testexersize.dirscan.utils.Prefix;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0){
            System.out.println("Params not specified. Usage:");
            System.out.println("dirscan foldertosearch - folderToExclude -F filesToExclude");
            return;
        }
        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        commandLineOptions.parseCommand();


        System.out.println(Prefix.valueOf("DASH"));

//        final Map<String, List<String>> params = new HashMap<>();
//
//        List<String> options = new ArrayList<>();
//
//        for (int i = 0; i < args.length; i++) {
//            final String a = args[i];
//
//            if (a.equals("-")) {
//                if (a.length() < 2) {
//                    System.err.println("Error at argument " + a);
//                    return;
//                }
//
//
//                params.put(a.substring(1), options);
//            }
//            else if (options != null) {
//                options.add(a);
//            }
//            else {
//                params.put(a,options);
//                return;
//            }
//        }


    }
}