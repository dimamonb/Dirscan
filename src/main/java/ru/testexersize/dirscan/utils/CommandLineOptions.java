package ru.testexersize.dirscan.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.*;

public class CommandLineOptions {
    private final char COMMAND_PREFIX = '-';
    private String[] args;
    private int cmdIndex; //
    private Map<String, List<String>> commandsAndValues = new HashMap<>(); //Ключ - команда, значение - список папок, файлов...
    private List<String> paths; //Список папок для поиска и исключения



    public CommandLineOptions(String[] args) {
        this.args = args;
    }
    /**
     * Если используется команда для указания папок для поиска (javaprogram -s "folder" "folder")
     * методы initFirstElementsOfMap() parseCmdArgs() можно убрать, а этот раскомментировать
     */
//    public Map<String, List<String>> parseCmdArgs() {
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].charAt(0) == '-') {
//                paths = new ArrayList<>();
//                commandsAndValues.put(args[i], paths);
//            } else {
//                paths.add(args[i]);
//            }
//        }
//        return commandsAndValues;
//    }

    /**
     * Выборка первых папок для поиска. Если не используется команда для поиска файлов. Например javaprogram -s "folder" "folder"
     */
    public void initFirstElementsOfMap() {
        paths = new ArrayList<>();
        for (int i = 0; i < this.args.length; i++) {
            if (args[i].charAt(0) == COMMAND_PREFIX) {
                cmdIndex = i;
                break;
            }
            paths.add(args[i]);
        }
        commandsAndValues.put("s", paths);
    }

    public Map<String, List<String>> parseCmdArgs() {
        initFirstElementsOfMap();
        for (int i = cmdIndex; i < this.args.length; i++) {
            if (args[i].charAt(0) == COMMAND_PREFIX) {
                paths = new ArrayList<>();
                commandsAndValues.put(args[i], paths);
            } else if (cmdIndex!=0){
                paths.add(args[i]);
            }
        }
        return commandsAndValues;
    }

}
