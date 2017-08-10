package com.toy.tran.data.extract.file;

import com.toy.thread.ThreadUtil;
import com.toy.tran.data.extract.Extract;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.Collections.synchronizedList;

/**
 * Created by Administrator on
 * 2017/7/17.
 */
public class FileExtract implements Extract {
    private DataFileReader fileReader;
    private ReaderFactery readerFactery;

    public FileExtract(ReaderFactery readerFactery, String dir, SerDe serDe, int threadnum) {
    }

    @Override
    public void close() {
    }

    @Override
    public ByteBuffer next() {

        return null;
    }


}

class FileScheduler {
    ReaderFactery readerFactery;
    private List<File> filesList = synchronizedList(new ArrayList<>());
    private ExecutorService threadPool;

    FileScheduler(ReaderFactery readerFactery, String dir, int threadnum) {
        threadPool = ThreadUtil.newDaemonFixedThreadPool(threadnum, "fileScheduler");
        fileDir(dir);
    }

    private void fileDir(String... dirs) {
        assert dirs != null;
        Arrays.asList(dirs).forEach(
                dir -> {
                    File file = new File(dir);
                    if (!file.exists())
                        throw new RuntimeException("file is not exists");
                    if (file.isFile())
                        filesList.add(file);
                    if (file.isDirectory())
                        fileDir(file.list());
                }
        );
    }

    private void close() {
    }
}
