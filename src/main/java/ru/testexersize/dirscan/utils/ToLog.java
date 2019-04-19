package ru.testexersize.dirscan.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;

public class ToLog {
    private static final String LOGFILE = "scan_dir_log";
    public static void writeMessage(String message){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        Path errorLog = Paths.get(LOGFILE + "_" + simpleDateFormat + ".log");
        try {
            Files.write(errorLog,(""+simpleDateFormat+ "  " + message).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {


        }
    }
}
