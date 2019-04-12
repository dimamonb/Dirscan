package ru.testexersize.dirscan.utils;

import java.util.Arrays;
import java.util.List;

public class CommandLineOptions {

    String[] args;
    List<String> validCommands = Arrays.asList("-", "-F");


    public CommandLineOptions(String[] args) {
        this.args = args;
    }

    public void parseCommand(){
        List<String> listArgs = Arrays.asList(args);
        if(listArgs.contains("-")){
            System.out.println(listArgs.indexOf("-"));
        }

        if(listArgs.containsAll(validCommands)){

        }

    }
}
