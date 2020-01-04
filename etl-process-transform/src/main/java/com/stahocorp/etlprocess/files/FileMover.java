package com.stahocorp.etlprocess.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Helper class to move files.
 */
public class FileMover {
    /**
     * This method is used to moving file from one directory to another. It also creates output directory if it doesn't exist.
     * @param i input path
     * @param o output path
     * @throws IOException
     */
    public static void move(Path i, Path o) throws IOException {
        if (!Files.exists(i.getParent())) {
            throw new IOException("Input directory doesn't exist");
        }

        if (!Files.exists(o.getParent())) {
            Files.createDirectories(o.getParent());
        }

        Files.move(i, o);
    }
}
