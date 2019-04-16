package ru.testexersize.dirscan.utils;

import java.util.*;

public class CommandLineOptions {

    private String[] args;
    private List<String> validCommands = Arrays.asList("-","-f", "-fe");
    private int cmdIndex;
    private Map<String, List<String>> returnCommandsAndValues = new HashMap<>(); //
    private List<String> paths; //Список папок для поиска и исключения



    public CommandLineOptions(String[] args) {
        this.args = args;
    }

    public void initFirstElementsOfMap(){
        paths = new ArrayList<>();
        for (int i = 0; i < this.args.length; i++) {
            if(validCommands.contains(args[i])) {
                cmdIndex = i;
                break;
            }
            paths.add(args[i]);
        }
        returnCommandsAndValues.put("s",paths);
    }

    public Map<String, List<String>> parseCommand(){
        initFirstElementsOfMap();
        for (int i = cmdIndex; i < this.args.length; i++) {
            if(validCommands.contains(args[i])){
                paths = new ArrayList<>();
                returnCommandsAndValues.put(args[i].substring(0), paths);
            } else {
                paths.add(args[i]);
            }
        }
        return returnCommandsAndValues;
    }

}
