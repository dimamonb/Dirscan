package ru.testexersize.dirscan;



import org.junit.Test;

import ru.testexersize.dirscan.utils.CommandLineOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DirScanTest {


    @Test
    public void commandLineOptionsTest(){
        CommandLineOptions commandLineOptions = new CommandLineOptions(new String[]{
                "\\epbyminsd0235\\Video Materials",
                "\\EPUALVISA0002.kyiv.com\\Workflow\\ORG\\Employees\\Special",
                "\\EPUALVISA0002.kyiv.com\\Workflow\\ORG\\Employees\\Lviv",
                "C:\\Windows",
                "-",
                "\\epbyminsd0235\\Video Materials",
                "\\EPUALVISA0002.kyiv.com\\Workflow\\ORG\\Employees\\Special",
                "\\EPUALVISA0002.kyiv.com\\Workflow\\ORG\\Employees\\Lviv"
        });

        Map<String, List<String>> map = new HashMap<>();
        map = commandLineOptions.parseCmdArgs();
        assertEquals(4, map.get("s").size());
        assertEquals(3,map.get("-").size());

    }

    @Test
    public void scanDirectoriesTest(){


    }

    @Test
    public void dirScanProgramTest(){

    }
}
