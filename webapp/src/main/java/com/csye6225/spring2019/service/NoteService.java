package com.csye6225.spring2019.service;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.entity.Note;
import com.csye6225.spring2019.impl.NoteServiceImpl;

import java.util.List;

public interface NoteService {

    boolean addNewNote(Note note);
    List<Note> findAll(int id);
    Note getNoteByNoteId (int id);

}
