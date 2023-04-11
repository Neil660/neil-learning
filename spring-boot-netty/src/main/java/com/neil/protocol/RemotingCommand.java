package com.neil.protocol;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:01
 * @Version 1.0
 */
public class RemotingCommand {
    private byte[] body;

    public RemotingCommand(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new String(this.body);
    }
}
