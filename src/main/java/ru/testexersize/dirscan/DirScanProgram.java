package ru.testexersize.dirscan;


import ru.testexersize.dirscan.utils.CommandLineOptions;
import ru.testexersize.dirscan.utils.DirScanPorcessor;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirScanProgram {
    private static List<String> validCommands = Arrays.asList("-", "-f", "-fe");

    public static void main(String[] args) throws IOException {

        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        Map<String, List<String>> filesAndFolders = commandLineOptions.parseCmdArgs();

        if (args.length == 0) {
            System.out.println("Params not specified. Usage:");
            System.out.println("dirscan foldertosearch - folderToExclude -F filesToExclude");
            return;
        }


        Timer timer = new Timer();
        timer.schedule(new TimerForConsole('s'), 0, 6000);
        timer.schedule(new TimerForConsole('m'), 60000, 60000);


        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long startTime = System.currentTimeMillis();
        for (String s : filesAndFolders.get("s")) {
            Path walkDir = Paths.get(s);
            DirScanPorcessor dirScanPorcessor = new DirScanPorcessor(walkDir, filesAndFolders);
            executorService.submit(dirScanPorcessor);
        }

        executorService.shutdown();
        timer.cancel();
        System.out.println();
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime-startTime);
        System.out.println("Scan time: " + elapsedTime +"ms");

    }

    public static class TimerForConsole extends TimerTask {

        char infoType;

        public TimerForConsole(char infoType) {
            this.infoType = infoType;
        }

        public void run() {
            switch (infoType) {
                case 'm':
                    System.out.print("|");
                case 's':
                    System.out.print(".");
            }
        }
    }
}


