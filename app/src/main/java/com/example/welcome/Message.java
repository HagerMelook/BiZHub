package com.example.welcome;


public class Message {
    private String content;
    private String senderId;
    private String currentTime;
    private long timeStamp;
    private String receiverId;
    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Message() {
    }

    public Message(String content, String senderId, String receiverId, String currentTime, long timeStamp) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.currentTime = currentTime;
        this.timeStamp = timeStamp;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getContent() {
        return content;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}