package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;

@Service
public class NoteService {

    private NoteMapper noteMapper;
   
    public Boolean addNewNote(Note file) {
        int addedNote = noteMapper.insertNote(file);
        
        return addedNote > 0;
    }

    public Boolean removeNote(String filename) {
        int noteid = noteMapper.getNoteIdByNotetitle(filename);
        return noteMapper.deleteNoteByNoteId(noteid);
    }

    public Boolean exchangefile(Note file) {
        int noteid = noteMapper.getNoteIdByNotetitle(file.getNotetitle());
        return noteMapper.updateNoteByNoteId(noteid, file);
    }

    public Note fetchNote(String filename) {
        return noteMapper.getNoteByNotetitle(filename);
    }
}
