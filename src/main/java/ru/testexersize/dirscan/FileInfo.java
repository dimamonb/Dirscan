package ru.testexersize.dirscan;

import java.text.SimpleDateFormat;
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
        StringBuilder retval = new StringBuilder(String.format("[\nfile=%s\ndate=%s\nsize=%s]",fileNameAndPath,fileDate,fileSize));
        String text = String.format("[\nfile=%s\ndate=%s\nsize=%s]",fileNameAndPath,fileDate,fileSize);
        String text1 = text.replace("[\n\r]", "");
        return  text1;
//                "[\nfile = " + fileNameAndPath + "\n" +
//                "date = " + fileDate + "\n" +
//                "size = " + fileSize +"\n" +
//                "]";
    }

    @Override
    public int compareTo(FileInfo o) {
        return this.fileNameAndPath.compareTo(o.fileNameAndPath);
    }
}
