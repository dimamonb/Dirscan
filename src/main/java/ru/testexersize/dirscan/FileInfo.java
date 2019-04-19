package ru.testexersize.dirscan;


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
        StringBuilder sb = new StringBuilder();
        sb.append("[\nfile = ");
        sb.append(fileNameAndPath);
        sb.append("\ndate = ");
        sb.append(fileDate);
        sb.append("\nsize = ");
        sb.append(fileSize);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int compareTo(FileInfo o) {
        return this.fileNameAndPath.compareTo(o.fileNameAndPath);
    }
}
