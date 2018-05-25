package com.example.lhd.myonerowcode.entity;

/**
 * Created by lhd on 2018/5/12.
 */

public class MessagesItem {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String msgContent;
    private int msgType;

    public MessagesItem(String content, int type) {
        this.msgContent = content;
        this.msgType = type;

    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }


    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
