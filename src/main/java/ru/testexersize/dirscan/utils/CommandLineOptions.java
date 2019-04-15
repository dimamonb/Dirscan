package ru.testexersize.dirscan.utils;

import java.util.*;

public class CommandLineOptions {

    //private List<String> args;
    private String[] args;
    private List<String> validCommands = Arrays.asList(" ","-", "-F");
    private int cmdIndex;
    Map<String, List<String>> returnCommandsAndValues = new HashMap<>();
    List<String> paths ;
    public CommandLineOptions(String[] args) {
        //this.args = Arrays.asList(args);
        this.args = args;
    }

    public void initFirstElementsOfMap(){
        paths = new ArrayList<>();
        for (int i = 0; i < this.args.length; i++) {
            if(args[i].startsWith("-")) {
                cmdIndex = i;
                break;
            }
            paths.add(args[i]);
            returnCommandsAndValues.put("s",paths);

        }
    }
    public Map<String, List<String>> parseCommand(){
        initFirstElementsOfMap();


        for (int i = cmdIndex; i < this.args.length; i++) {

            if(args[i].startsWith("-")){
                paths = new ArrayList<>();
                returnCommandsAndValues.put(args[i].substring(0), paths);
            } else {

                paths.add(args[i]);
                //returnCommandsAndValues.put(args[i].substring(0), paths);
            }
        }

        //returnCommandsAndValues.put(args[i].substring(0), paths);



        return returnCommandsAndValues;
    }
}
