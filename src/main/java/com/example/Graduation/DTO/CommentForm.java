package com.example.Graduation.DTO;

public class CommentForm {
    private String idx;
    private String number;
    private String comment;
    private String write_id;
    private String write_time;
    private String user;
    public CommentForm(String idx, String number, String comment, String write_id, String write_time,String user) {
        this.idx = idx;
        this.number = number;
        this.comment = comment;
        this.write_id = write_id;
        this.write_time = write_time;
        this.user = user;
    }
    public String getUser(){
        return user;
    }
    public String getIdx() {
        return idx;
    }

    public String getNumber() {
        return number;
    }

    public String getComment() {
        return comment;
    }

    public String getWrite_id() {
        return write_id;
    }

    public String getWrite_time() {
        return write_time;
    }

    @Override
    public String toString() {
        return "CommentForm{" +
                "idx=" + idx +
                ", number='" + number + '\'' +
                ", comment='" + comment + '\'' +
                ", write_id='" + write_id + '\'' +
                ", write_time='" + write_time + '\'' +
                '}';
    }

}
