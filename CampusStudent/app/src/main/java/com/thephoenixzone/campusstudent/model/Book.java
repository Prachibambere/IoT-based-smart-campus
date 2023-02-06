package com.thephoenixzone.campusstudent.model;

public class Book {
    private String title;
    private String author;
    private String datetime;
    private String returnDate;
    private String duecharges;
    private String status;


    public Book(String title, String author, String datetime, String returnDate, String duecharges, String status) {
        this.title = title;
        this.author = author;
        this.datetime = datetime;
        this.returnDate = returnDate;
        this.duecharges = duecharges;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDuecharges() {
        return duecharges;
    }

    public void setDuecharges(String duecharges) {
        this.duecharges = duecharges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
