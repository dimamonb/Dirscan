package ru.testexersize.dirscan.utils;

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

    public Map<String, List<String>> parseCmdArgs() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                paths = new ArrayList<>();
                commandsAndValues.put(args[i], paths);
            } else {
                paths.add(args[i]);
            }
        }
        return commandsAndValues;
    }


}
