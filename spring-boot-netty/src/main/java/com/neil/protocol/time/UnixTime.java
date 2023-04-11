package com.neil.protocol.time;

import java.util.Date;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/22 14:21
 * @Version 1.0
 */
public class UnixTime {
    private final long value;
    private byte[] body;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime(byte[] body) {
        this(1000L);
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new String(this.body);
    }
}
