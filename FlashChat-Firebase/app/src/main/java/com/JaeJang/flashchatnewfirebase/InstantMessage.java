package com.JaeJang.flashchatnewfirebase;

/**
 * Created by jaeja on 2018-01-10.
 */

public class InstantMessage {

    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstantMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
