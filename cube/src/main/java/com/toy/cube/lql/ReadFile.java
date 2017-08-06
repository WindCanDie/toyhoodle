package com.toy.cube.lql;

import java.io.*;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class ReadFile {
    private BufferedReader reader;

    public ReadFile(String dir) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(new File(dir)));
    }

    public String readLien() throws IOException {
        String line = reader.readLine();
        if (line != null) {
            line = line.trim();
            if (line.startsWith("#") || line.length() == 0 || "".equals(line))
                line = readLien();
        }
        return line;
    }

}
