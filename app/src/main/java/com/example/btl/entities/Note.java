package com.example.btl.entities;

import java.io.Serializable;

public class Note implements Serializable {

    private int id;
    private String title;
    private String subtitle;
    private String dateTime;
    private String noteText;
    private String imagePath;
    private String color;
    private long lastTime;

    public Note() {

    }

    public Note(int id, String title, String subtitle, String dateTime, String noteText,
                String imagePath, String color, long lastTime) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.dateTime = dateTime;
        this.noteText = noteText;
        this.imagePath = imagePath;
        this.color = color;
        this.lastTime = lastTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return title + ": " + dateTime;
    }
}
