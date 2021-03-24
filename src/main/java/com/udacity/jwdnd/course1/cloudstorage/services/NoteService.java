package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, FileMapper fileMapper, CredentialMapper credentialMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }

    public int addOrUpdateNote(Note note){
        if(noteMapper.getNoteById(note.getNoteId())==null){
            return noteMapper.addNote(note);
        }
        else return noteMapper.editNote(note);
    }


    public int deleteNote(int noteId){
        return noteMapper.deleteNote(noteId);
    }


    public List<Note> getUserNotes(Integer userId){
        return noteMapper.getAllUserNotes(userId);
    }



}
