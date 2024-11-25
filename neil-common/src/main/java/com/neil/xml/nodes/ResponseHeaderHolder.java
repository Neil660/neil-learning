package com.neil.xml.nodes;

/*
 * @Classname ResponseHeaderHolder
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public final class ResponseHeaderHolder implements javax.xml.rpc.holders.Holder {
    public ResponseHeader value;

    public ResponseHeaderHolder() {
    }

    public ResponseHeaderHolder(ResponseHeader value) {
        this.value = value;
    }

}