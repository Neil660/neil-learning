package com.multi.thread.example.case8;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.concurrent.Exchanger;

/**
 * @Decription main0：main线程创建PipedInputStream，跟rss-reader线程创建的PipedOutputStream相关联，实现数据互通
 * @Decription main1：Exchanger交换数据的简单应用
 * @Author NEIL
 * @Date 2023/1/30 17:57
 * @Version 1.0
 */
public class TestRunner {
    public static void main(String[] args) throws Exception {
        //main0(args);
        main1(args);
    }

    // channel入口
    public static void main0(String[] args) throws Exception {
        final int argc = args.length;
        String url = argc > 0 ? args[0] : "http://lorem-rss.herokuapp.com/feed";

        // 从网络加载RSS数据
        InputStream in = ConcurrentRSSReader.loadRSS(url);

        // 从输入流中解析XML数据
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(in);

        // 读取XML中的数据
        Element eleRss = (Element) document.getFirstChild();
        Element eleChannel = (Element) eleRss.getElementsByTagName("channel").item(
                0);
        Node ndTtile = eleChannel.getElementsByTagName("title").item(0);
        String title = ndTtile.getFirstChild().getNodeValue();
        System.out.println(title);
        // 省略其他代码
    }

    // Exchanger入口
    public static void main1(String[] args) throws Exception {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = "111";
                try {
                    System.out.println("First thread's data is " + data + " before exchangering.");
                    // 阻塞，等待另外一个exchanger输入数据
                    String excData = exchanger.exchange(data);
                    System.out.println("First thread's data is " + excData + " after exchangering.");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = "222";
                try {
                    System.out.println("Second thread's data is " + data + " before exchangering.");
                    String excData = exchanger.exchange(data);
                    System.out.println("Second thread's data is " + excData + " after exchangering.");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
