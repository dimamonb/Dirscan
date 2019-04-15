package ru.testexersize.dirscan;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ScanDirectories extends SimpleFileVisitor<Path> {
    private final String FILENAME = "scan_results.txt";
    Map<String, List<String>> excludeFilesAndFolders;
    List<FileInfo> fileInfoList = new ArrayList<>();

    public ScanDirectories(Map<String, List<String>> excludeFilesAndFolders) {
        this.excludeFilesAndFolders = excludeFilesAndFolders;
    }

    public void writeFileInfoToFile() {

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
        for (String s : excludeFilesAndFolders.get("-")) {
            Path f = Paths.get(s);
            if (dir.getFileName().toString().equals(f.getFileName().toString())) {
                return FileVisitResult.SKIP_SUBTREE;
            }
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toString() + " -------" + attrs.creationTime() + " -------" + attrs.size());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        fileInfoList.add(new FileInfo(file.toString(), simpleDateFormat.format(attrs.lastModifiedTime().toMillis()), attrs.size()));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return super.postVisitDirectory(dir, exc);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);
    }
}
