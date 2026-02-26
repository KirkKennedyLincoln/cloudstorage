package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private byte[] filedata;

    public File(
        String filename,
        String contenttype,
        Long filesize,
        Integer userid,
        byte[] filedata
    ) {

        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public String getContenttype() {
        return contenttype;
    }
    public byte[] getFiledata() {
        return filedata;
    }
    public String getFilename() {
        return filename;
    }
    public Long getFilesize() {
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
    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
