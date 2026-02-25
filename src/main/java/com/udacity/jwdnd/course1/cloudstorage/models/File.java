package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    
    public File(
        String filename,
        String contenttype,
        long filesize,
        Integer userid,
        byte[] filedata
    ) {}
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public String getContenttype() {
        return contenttype;
    }
    public byte[] getFiledata() {
        return filedata;
    }
    public String getFilename() {
        return filename;
    }
    public String getFilesize() {
        return filesize;
    }
    public Integer getUserid() {
        return userid;
    }
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }
    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
