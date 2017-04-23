package br.com.opining.domain;

import java.util.Calendar;

public class Message {

    private String content;
    private User author;

    public Message(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public Calendar getTimestamp() {
        return Calendar.getInstance();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
