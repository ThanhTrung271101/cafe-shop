package com.example.finalproject.Models;

public class Notification {
    String Id;
    String Content;
    String Time;
    String Title;
    Boolean IsRead;
    public Notification(){}
    public Notification(String Id,String Content,String Time,String Title,Boolean IsRead){
        this.Id=Id;
        this.Content=Content;
        this.Title=Title;
        this.Time=Time;
        this.IsRead=IsRead;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Boolean getRead() {
        return IsRead;
    }

    public void setRead(Boolean read) {
        IsRead = read;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
