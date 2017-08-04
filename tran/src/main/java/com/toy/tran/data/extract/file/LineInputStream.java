package com.toy.tran.data.extract.file;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on
 * 2017/8/3.
 */
public class LineInputStream implements Closeable {
    private static String FILE_LINE_SPLIT;
    private static String FILE_ENCOD;
    private final String path;
    private FileInputStream file;
    private StringBuffer buff = new StringBuffer();
    private List<String> buffsplit = new LinkedList<>();
    private boolean end = false;

    public LineInputStream(String path, String fileLineSplit, String fileEncod) throws FileNotFoundException {
        FILE_LINE_SPLIT = fileLineSplit;
        FILE_ENCOD = fileEncod;
        this.path = path;
        file = new FileInputStream(new File(path));
    }

    /**
     * @return file END return null
     */

    public String raedLine() throws IOException {
        if (end) {
            return null;
        }
        byte[] buffs = new byte[1024 * 100];
        if (buffsplit != null && buffsplit.size() > 0) {
            return buffsplit.remove(0);
        }
        int i;
        while ((i = file.read(buffs)) != -1) {
            buff.append(new String(buffs, 0, i, FILE_ENCOD));
            String[] splits = buff.toString().split(FILE_LINE_SPLIT);
            if (splits.length > 2) {
                String v = splits[0];
                buff = new StringBuffer(splits[splits.length - 1]);
                buffsplit.addAll(Arrays.asList(splits).subList(1, splits.length - 1));
                return v;
            }
        }
        end = true;
        return buff.toString();
    }

    @Override
    public void close() throws IOException {
        buff = null;
        buffsplit = null;
        file.close();
    }

}
