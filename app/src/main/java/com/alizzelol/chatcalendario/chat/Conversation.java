package com.alizzelol.chatcalendario.chat;

import java.util.Date;
import java.util.List;

public class Conversation {
    private String conversationId;
    private String lastMessage;
    private String lastSender;
    private Date lastTimestamp;
    private List<String> users;
    private List<String> deletedBy;

    public Conversation() {}

    public Conversation(String conversationId, String lastSender, String lastMessage, Date lastTimestamp, List<String> users, List<String> deletedBy) {
        this.conversationId = conversationId;
        this.lastSender = lastSender;
        this.lastMessage = lastMessage;
        this.lastTimestamp = lastTimestamp;
        this.users = users;
        this.deletedBy = deletedBy;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastSender() {
        return lastSender;
    }

    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }

    public Date getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(Date lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(List<String> deletedBy) {
        this.deletedBy = deletedBy;
    }
}

