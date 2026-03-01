package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(
        UserService userService,
        NoteMapper noteMapper
    ) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }
   
    public Boolean addNewNote(Note file, String username) {
        User user = this.userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        int addedNote = noteMapper.insertNote(file, userId);
        return addedNote > 0;
    }

    public Boolean removeNote(String notetitle, String username) {
        User user = this.userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        int noteid = noteMapper.getNoteIdByNotetitle(notetitle, userId);
        return noteMapper.deleteNoteByNoteId(noteid);
    }

    public Boolean exchangeNote(Note note, String username) {
        return noteMapper.updateNoteByNoteId(note.getNoteid(), note);
    }

    public Note fetchNote(String filename) {
        return noteMapper.getNoteByNotetitle(filename);
    }

    public Note[] fetchAllNotetitles(String username) {
        User user = this.userService.getUserByUsername(username);
        Integer userId = user.getUserId();

        Note[] notes = this.noteMapper.getAllNotes(userId);
        if (null == notes) {
            return null;
        }

        return notes;
    }

    public Boolean updateNote(Note note, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateNote'");
    }
}
