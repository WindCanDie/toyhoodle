package com.toy.tran.data.extract.file;

import com.toy.thread.ThreadUtil;
import com.toy.tran.data.extract.Extract;
import com.toy.tran.data.extract.SerDe;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.synchronizedList;

/**
 * Created by Administrator on
 * 2017/7/17.
 */
public class FileExtract implements Extract {
    private SerDe serDe;
    private FileScheduler scheduler;
    private LinkedBlockingQueue<ByteBuffer> dataBuffers;
    static final ByteBuffer POISON_PILL = ByteBuffer.allocate(0);

    public FileExtract(String dir, SerDe serDe, int threadnum) {
        this.serDe = serDe;
        this.scheduler = new FileScheduler(dir, threadnum, serDe);
        dataBuffers = new LinkedBlockingQueue<>();
    }

    @Override
    public void close() {
        scheduler.close();
        dataBuffers = null;
    }

    @Override
    public ByteBuffer next() {
        ByteBuffer data;
        for (; ; ) {
            if ((data = dataBuffers.poll()) != null)
                return data;
            if (data.equals(POISON_PILL))
                return null;
        }
    }


}

class FileScheduler {
    private List<File> filesList = synchronizedList(new ArrayList<>());
    private ExecutorService threadPool;
    private AtomicInteger endTread;
    private SerDe serDe;
    private boolean close = false;

    FileScheduler(String dir, int threadnum, SerDe serDe) {
        threadPool = ThreadUtil.newDaemonFixedThreadPool(threadnum, "fileScheduler");
        endTread = new AtomicInteger(threadnum);
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

    private void start(final LinkedBlockingQueue<ByteBuffer> dataBuffers) {
        filesList.forEach(dir -> {
            if (close) close();
            threadPool.execute(() -> {
                try {
                    LineInputStream inputStream = new LineInputStream(dir.getPath(), "", "");
                    String data;
                    while ((data = inputStream.raedLine()) != null) {
                        dataBuffers.put(serDe.serialize(data));
                    }
                    if (close && endTread.addAndGet(-1) == 1) {
                        dataBuffers.put(FileExtract.POISON_PILL);
                        close();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });
        this.close = true;
    }


    void close() {
        this.close = true;
        threadPool.shutdown();
    }
}
