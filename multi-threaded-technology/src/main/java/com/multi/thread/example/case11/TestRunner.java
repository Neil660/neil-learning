package com.multi.thread.example.case11;

import com.multi.thread.utils.Debug;
import com.multi.thread.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/2/2 16:55
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) throws Exception {
        final FileBatchUploader uploader = new FileBatchUploader("localhost", "datacenter", "abc123",
                "/home/datacenter/tmp/") {
            @Override
            public void init() throws Exception {
                Debug.info("init...");
                super.init();
            }

            @Override
            protected void upload(File file) throws Exception {
                super.upload(file);
            }

            @Override
            public void close() throws IOException {
                Debug.info("close...");
                super.close();
            }
        };

        uploader.init();

        Set<File> files = new HashSet<File>();
        files.add(new File("/home/viscent/tmp/incomingX/message1.dat"));
        files.add(new File("/home/viscent/tmp/incomingX/message2.dat"));
        files.add(new File("/home/viscent/tmp/incomingX/message3.dat"));
        files.add(new File("/home/viscent/tmp/incomingX/message4.dat"));
        files.add(new File("/home/viscent/tmp/incomingX/message5.dat"));
        uploader.uploadFiles(files);

        Tools.delayedAction("", new Runnable() {
            @Override
            public void run() {
                Tools.silentClose(uploader);
            }
        }, 120);
    }
}
