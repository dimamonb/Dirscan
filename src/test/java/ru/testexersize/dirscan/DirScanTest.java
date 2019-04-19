package ru.testexersize.dirscan;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.Rule;
import org.junit.Test;

import ru.testexersize.dirscan.utils.CommandLineOptions;
import ru.testexersize.dirscan.utils.ScanProcessor;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class DirScanTest {



    @Test
    public void commandLineOptionsTest(){
        CommandLineOptions commandLineOptions = new CommandLineOptions(new String[]{
                "-s",
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
        assertEquals(4, map.get("-s").size());
        assertEquals(3,map.get("-").size());

    }


    @Test
    public void scanDirectoriesTest() throws IOException {

        FileSystem fileSystem = MemoryFileSystemBuilder.newWindows().build("fs");

        Path incDir = fileSystem.getPath("foo");
        Files.createDirectory(incDir);


        List<String> exclDirs = new ArrayList<>();
        exclDirs.add("bar");
        exclDirs.add("bar1");

        for (int i = 0; i < 100000; i++) {
            Path file = Files.createFile(fileSystem.getPath(incDir+"\\testFile"+i+".txt"));
        }

        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(20);
        ScanProcessor sp = new ScanProcessor(incDir, exclDirs);
        forkJoinPool.execute(sp);
        do
        {
            System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
            System.out.printf("******************************************\n");
            try
            {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } while (!sp.isDone());
        forkJoinPool.shutdown();
        long theend = System.currentTimeMillis();

        HashSet<FileInfo> results = sp.join();
        long elapsedTime = theend - start;
        System.out.println("\nFiles found: " + results.size());
        System.out.println("Processed time: " + elapsedTime + " ms");
    }


}
