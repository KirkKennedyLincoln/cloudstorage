package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.models.File;

public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{file.filename}, #{file.contenttype}, #{file.filesize}, #{file.userid}, #{file.filedata})")
    int insertFile(@Param("file") File file);

    @Select("SELECT filename, contenttype, filesize, userid, filedata FROM FILES WHERE filename = #{filename}")
    public File getFileByFilename(@Param("filename") String filename);

    @Select("SELECT fileId FROM FILES WHERE filename = #{filename}")
    public Integer getFileIdByFilename(@Param("filename") String filename);

    @Update("UPDATE FILES SET (\n" + 
    "filename = #{file.filename}, contenttype = #{file.contenttype}, filesize = #{file.filesize}, userid = #{file.userid}, filedata = #{file.filedata})\n" +
    "WHERE fileId = #{fileId}")
    Boolean updateFileByFileId(@Param("fileId") Integer fileId, @Param("file") File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public Boolean deleteFileByFileId(@Param("fileId") Integer fileId);
}
