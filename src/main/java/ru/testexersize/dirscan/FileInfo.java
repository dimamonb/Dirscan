package ru.testexersize.dirscan;

import java.util.Date;

public class FileInfo {
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
        return "[" + "\n" +
                "file='" + fileNameAndPath + "\n" +
                "date=" + fileDate + "\n" +
                "size=" + fileSize + "\n" +
                ']';
    }
}
