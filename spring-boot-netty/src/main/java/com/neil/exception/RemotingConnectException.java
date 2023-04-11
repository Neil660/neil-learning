package com.neil.exception;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:49
 * @Version 1.0
 */
public class RemotingConnectException extends RemotingException {
    public RemotingConnectException(String addr) {
        this(addr, null);
    }

    public RemotingConnectException(String addr, Throwable cause) {
        super("connect to " + addr + " failed", cause);
    }
}
