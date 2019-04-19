package ru.testexersize.dirscan;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.nio.file.FileSystem;

final class FileSystemRule implements TestRule {

    private FileSystem fileSystem;

    FileSystem getFileSystem() {
        return this.fileSystem;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                fileSystem = MemoryFileSystemBuilder.newEmpty().build();
                try {
                    base.evaluate();
                } finally {
                    fileSystem.close();
                }
            }

        };
    }

}
