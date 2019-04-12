package ru.testexersize.dirscan;

import java.util.Date;

public class FileInfo {
    private String fileNameAndPath;
    private Date fileDate;
    private int fileSize;

    public FileInfo(String fileNameAndPath, Date fileDate, int fileSize) {
        this.fileNameAndPath = fileNameAndPath;
        this.fileDate = fileDate;
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "[" +
                "file='" + fileNameAndPath + '\'' +
                "date=" + fileDate + '\'' +
                "size=" + fileSize +
                ']';
    }
}
