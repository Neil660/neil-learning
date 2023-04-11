package com.neil.exception;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:49
 * @Version 1.0
 */
public class RemotingException extends Exception {

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
