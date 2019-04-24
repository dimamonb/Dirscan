package ru.testexersize.dirscan.utils;

import ru.testexersize.dirscan.FileInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveTask;

/**
 * Класс для сканирования папок.
 * Для реализации моногопоточности используем RecursiveTask. Который позволяет нам разбить
 * поиск на потоки(fork), а в конце собрать всё в кучу (join)
 */
public class ScanProcessorBQ extends RecursiveTask<PriorityBlockingQueue<FileInfo>> {
    private static final String FILENAME = "scan_results.txt";
    private Path dir;
    private List<String> excludedDirs;
    //Коллекция в которую записываем результаты сканирования. Используем HashSet. Упорядоченная коллекция.
    //Быстрее добавлять элементы чем в TreeSet, т.к. реализована с помощью HashMap
    //HashSet<FileInfo> fileInfoList = new HashSet<>();
    PriorityBlockingQueue<FileInfo> fileInfoList = new PriorityBlockingQueue<>();

    public ScanProcessorBQ(Path dir, List<String> excludedDirs) {
        this.dir = dir;
        this.excludedDirs = excludedDirs;
    }

    /**
     * В методе compute класса RecursiveTask осуществляем обход дерева папок. Для этого используем
     * метод walkFileTree.
     *
      * @return
     */
    @Override
    protected PriorityBlockingQueue<FileInfo> compute() {

        final List<ScanProcessorBQ> walks = new ArrayList<>();
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                /**
                 * В этом методе исключаем папки из поиска если папка совпадает с переданными из main
                 * FileVisitResult.SKIP_SUBTREE
                 */
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(ScanProcessorBQ.this.dir)) {
                        ScanProcessorBQ w = new ScanProcessorBQ(dir, excludedDirs);
                        w.fork();
                        walks.add(w);
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    if (excludedDirs != null && excludedDirs.contains(dir.toString())) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * Записываем в коллекцию найденные файлы. В этом методе можно реализовать фильтры для исключения файлов
                 * по определенным параметрам.
                  * @param file
                 * @param attrs
                 * @return
                 * @throws IOException
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    fileInfoList.put(new FileInfo(file.toString(), simpleDateFormat.format(attrs.lastModifiedTime().toMillis()), attrs.size()));

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    ToLog.writeMessage(exc.getMessage());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    writefoToFile(fileInfoList);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            ToLog.writeMessage(e.getMessage());
        }
        addResultsFromTasks(fileInfoList, walks);
        return fileInfoList;
    }

    private void addResultsFromTasks(PriorityBlockingQueue<FileInfo> list, List<ScanProcessorBQ> walks)
    {
        for (ScanProcessorBQ item : walks)
        {
            list.addAll(item.join());
        }
    }

    /**
     * Запись в файл
     * @param fileInfoList
     */
    public static void writefoToFile(PriorityBlockingQueue<FileInfo> fileInfoList) {
        Path fileP = Paths.get(FILENAME);
        Charset charset = Charset.forName("utf-8");

        try (BufferedWriter writer = Files.newBufferedWriter(fileP, charset, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            for (FileInfo fileInfo : fileInfoList) {
                writer.write(fileInfo.toString());
                //writer.newLine();
            }
        } catch (IOException e) {
            ToLog.writeMessage(e.getMessage());
        }
    }
}
