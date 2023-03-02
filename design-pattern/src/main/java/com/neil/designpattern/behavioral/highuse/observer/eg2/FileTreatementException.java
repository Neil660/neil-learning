package com.neil.designpattern.behavioral.highuse.observer.eg2;

/**
 * @Decription 向文件中add时发生的异常类
 * @Author NEIL
 * @Date 2023/2/9 10:24
 * @Version 1.0
 */
public class FileTreatementException extends RuntimeException {
    public FileTreatementException() {}

    public FileTreatementException(String msg) {
        super(msg);
    }
}
