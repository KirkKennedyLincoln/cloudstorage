package com.udacity.jwdnd.course1.cloudstorage.models;

public class Note {

    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    public Integer getNoteid() {
        return noteid;
    }
    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }
    public String getNotedescription() {
        return notedescription;
    }
    public String getNotetitle() {
        return notetitle;
    }
    public Integer getUserid() {
        return userid;
    }
    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
