package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;

public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{note.notetitle}, #{note.notedescription}, #{note.userid})")
    int insertNote(@Param("note") Note note);

    @Select("SELECT noteid FROM NOTES WHERE notetitle = #{notetitle}")
    public Note getNoteByNotetitle(@Param("notetitle") String notetitle);

    @Select("SELECT noteid FROM NOTES WHERE notetitle = #{notetitle}")
    public Integer getNoteIdByNotetitle(@Param("notetitle") String notetitle);

    @Update("UPDATE NOTES SET (\n" + 
    "notetitle = #{note.notetitle}, notedescription = #{note.notedescription}, userid = #{note.userid})\n" +
    "WHERE noteid = #{noteid}")
    Boolean updateNoteByNoteId(@Param("noteid") Integer noteid, @Param("note") Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public Boolean deleteNoteByNoteId(@Param("noteid") Integer noteid);
}
