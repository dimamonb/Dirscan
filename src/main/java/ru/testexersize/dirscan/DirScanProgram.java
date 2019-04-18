package ru.testexersize.dirscan;


import ru.testexersize.dirscan.utils.CommandLineOptions;
import ru.testexersize.dirscan.utils.ScanProcessor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class DirScanProgram {

    private static List<String> validCommands = Arrays.asList("-", "s");
    private static final int MAX_TREADS = 20;
    private static final String FILENAME = "scan_results.txt";

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Params not specified. Usage:");
            System.out.println("dirscan foldertosearch - folderToExclude -F filesToExclude");
            return;
        }

        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        Map<String, List<String>> filesAndFolders = commandLineOptions.parseCmdArgs();
        for (String item: filesAndFolders.keySet()) {
            if(!validCommands.contains(item)){
                System.out.printf("%s not a valid command", item);
                return;
            }
        }


        ScanProcessor scanProcessor=null;
        ForkJoinPool forkJoinPool = new ForkJoinPool(MAX_TREADS);


        Timer timer = new Timer();
        timer.schedule(new TimerForConsole('s'), 0, 6000);
        timer.schedule(new TimerForConsole('m'), 60000, 60000);

        for (String s : filesAndFolders.get("s")) {
            Path walkDir = Paths.get(s);
            scanProcessor = new ScanProcessor(walkDir, filesAndFolders.get("-"));
            forkJoinPool.execute(scanProcessor);
        }

        forkJoinPool.shutdown();

        HashSet<FileInfo> results = scanProcessor.join();
        System.out.println("\nFiles found: " + results.size());
        timer.cancel();
        writefoToFile(results);
    }

    //    ***********************************************************//
    public static void writefoToFile(HashSet<FileInfo> fileInfoList) {
        String fullFileName = FILENAME + System.currentTimeMillis() + ".txt";
        Path fileP = Paths.get(FILENAME);
        Charset charset = Charset.forName("utf-8");

        try (BufferedWriter writer = Files.newBufferedWriter(fileP, charset, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            for (FileInfo fileInfo : fileInfoList) {
                writer.write(fileInfo.toString());
                writer.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***************************************************************/


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


