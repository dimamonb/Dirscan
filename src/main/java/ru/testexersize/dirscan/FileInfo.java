package ru.testexersize.dirscan;

import java.util.Date;

public class FileInfo implements Comparable<FileInfo> {
    private String fileNameAndPath;
    private String fileDate;
    private long fileSize;

    public FileInfo(String fileNameAndPath, String fileDate, long fileSize) {
        this.fileNameAndPath = fileNameAndPath;
        this.fileDate = fileDate;
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "[" +
                "file='" + fileNameAndPath + "\n" +
                "date=" + fileDate + "\n" +
                "size=" + fileSize +
                "]";
    }

    @Override
    public int compareTo(FileInfo o) {
        return this.fileDate.compareTo(o.fileDate);
    }
}
