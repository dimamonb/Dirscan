package ru.testexersize.dirscan;

import ru.testexersize.dirscan.utils.FileDirFilter;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScanDirectories extends SimpleFileVisitor<Path> {
    private final String FILENAME = "scan_results.txt";

    private Map<String, List<String>> excludeFilesAndFolders;



    private List<FileInfo> fileInfoList = new ArrayList<>();

    public ScanDirectories(Map<String, List<String>> excludeFilesAndFolders) {
        this.excludeFilesAndFolders = excludeFilesAndFolders;
    }

    public List<FileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void writefoToFile() {

        Path fileP = Paths.get(FILENAME);
        Charset charset = Charset.forName("utf-8");

        try (BufferedWriter writer = Files.newBufferedWriter(fileP, charset)) {
            for (FileInfo fileInfo : fileInfoList) {
                writer.write(fileInfo.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        if (excludeFilesAndFolders.containsKey("-") && excludeFilesAndFolders.get("-").contains(dir.toString())) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        FileDirFilter fileDirFilter = new FileDirFilter(excludeFilesAndFolders);
        if(!fileDirFilter.accept(file.toFile())) {
            fileInfoList.add(new FileInfo(file.toString(), simpleDateFormat.format(attrs.lastModifiedTime().toMillis()), attrs.size()));
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if(fileInfoList.size() != 0) {
            writefoToFile();
        }
        if(exc != null){
            System.out.println("Error after process directory" + exc.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);
    }
}
