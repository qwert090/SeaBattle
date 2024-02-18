package org.packov;

import java.util.Objects;

public class GameWriter {
    private static GameWriter instance;
    private static String filename;

    private GameWriter() {
    }

    public static GameWriter getInstance() {
        if (Objects.isNull(instance)) {
            instance = new GameWriter();
            setFilename();
        }
        return instance;
    }

    public void write(String text) {
        // write
    }

    public static void close() {
        instance = null;
    }

    private static void setFilename() {
        // check folder
        // set filename
    }
}
