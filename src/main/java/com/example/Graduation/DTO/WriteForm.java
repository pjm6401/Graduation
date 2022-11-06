package com.example.Graduation.DTO;

import org.jetbrains.annotations.NotNull;

public class WriteForm {
    private String idx;

    private String title;

    private String file_name;

    private String file_path;

    private String content;

    private String id;

    private String write_time;

    public WriteForm(String idx, String title, String file_name, String file_path, String content, String id, String write_time) {
        this.idx = idx;
        this.title = title;
        this.file_name = file_name;
        this.file_path = file_path;
        this.content = content;
        this.id = id;
        this.write_time = write_time;
    }

    public String getIdx() {
        return idx;
    }

    public String getTitle() {
        return title;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getWrite_time() {
        return write_time;
    }
}
