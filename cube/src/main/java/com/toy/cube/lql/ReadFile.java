package com.toy.cube.lql;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class ReadFile {
    private BufferedReader reader;
    private AtomicInteger count;

    public ReadFile(String dir) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(new File(dir)));
        count = new AtomicInteger(0);
    }

    public String readLien() throws IOException {
        count.getAndAdd(1);
        String line = reader.readLine();
        if (line != null) {
            line = line.trim();
            if (line.startsWith("#") || line.length() == 0 || "".equals(line))
                line = readLien();
        }
        return line;
    }

    public int getLine() {
        return count.get();
    }
}
