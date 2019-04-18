package ru.testexersize.dirscan.utils;

import ru.testexersize.dirscan.FileInfo;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.RecursiveTask;

public class ScanProcessor extends RecursiveTask<HashSet<FileInfo>> {

    private Path dir;

    private List<String> excludedDirs;
    HashSet<FileInfo> fileInfoList = new HashSet<>();

    public ScanProcessor(Path dir, List<String> excludedDirs) {
        this.dir = dir;
        this.excludedDirs = excludedDirs;
    }


    @Override
    protected HashSet<FileInfo> compute() {
        final List<ScanProcessor> walks = new ArrayList<>();


        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(ScanProcessor.this.dir)) {
                        ScanProcessor w = new ScanProcessor(dir, excludedDirs);
                        w.fork();
                        walks.add(w);
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    if (excludedDirs != null && excludedDirs.contains(dir.toString())) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fileInfoList.add(new FileInfo(file.toString(), simpleDateFormat.format(attrs.lastModifiedTime().toMillis()), attrs.size()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return super.visitFileFailed(file, exc);
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (fileInfoList.size() != 0) {
                       //
                    }
                    if (exc != null) {
                        System.out.println("Error after process directory" + exc.getMessage());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });


        } catch (IOException e) {
            System.out.println("Error11: " + e.getMessage());;
        }
        addResultsFromTasks(fileInfoList, walks);
        return fileInfoList;
    }

    private void addResultsFromTasks(HashSet<FileInfo> list, List<ScanProcessor> walks)
    {
        for (ScanProcessor item : walks)
        {
            list.addAll(item.join());
        }
    }
}
