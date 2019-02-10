package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.entity.Note;
import com.csye6225.spring2019.filter.Verifier;
import com.csye6225.spring2019.service.NoteService;
import com.csye6225.spring2019.service.RegisterService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.csye6225.spring2019.repository.UserRepository;
import com.csye6225.spring2019.service.NoteService;
import com.csye6225.spring2019.service.RegisterService;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import java.util.ArrayList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;
    @Autowired
    RegisterService registerService;


    @GetMapping("/note")
    public Result<List<Note>> getAllNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        Result<List<Note>> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
        }
        else{
            account = registerService.findByEmail(account.getEmailAddress());
            Integer id = account.getId();
        }
        return res;
    }

    @PostMapping("/note")
    public Result<Note> postNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody Note note) throws IOException{
        Result<Note> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
        }
        else{
            if(Strings.isNullOrEmpty(note.getContent()) || Strings.isNullOrEmpty(note.getTitle())){
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST,"Bad Request");
            }
            httpServletResponse.setStatus(SC_CREATED);
           // httpServletResponse.se(SC_CREATED,"created");
            Note userNote = new Note();
            String content = note.getContent();
            String title = note.getTitle();
            userNote.setContent(content);
            userNote.setTitle(title);
            res.setData(userNote);
        }
        return res;
    }

    @GetMapping("/note/{id}")
    public Result<List<Note>> getCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int noteId) throws IOException{
        Result<List<Note>> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
        }
        else{
            res.setData(noteService.getNoteByNoteId(account, noteId));
        }
        return res;
    }

    @PutMapping("/note/{id}")
    public Result<Note> putCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int noteId, @RequestBody Note note) throws IOException{
        Result<Note> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
        }
        else{
            Note userNote = new Note();
            String content = note.getContent();
            String title = note.getTitle();
            int userId = note.getUserId();
            userNote.setContent(content);
            userNote.setTitle(title);
            userNote.setUserId(userId);
            noteService.updateNoteByNoteId(account,noteId);
            res.setData(userNote);
        }
        return res;
    }

    @DeleteMapping("/note/{id}")
    public Result<String> deleteCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int noteId) throws IOException {
        Result<String> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
        }
        else{
            String email = account.getEmailAddress();
            Account user = registerService.findByEmail(email);
            noteService.deleteNoteByNoteId(user,noteId);
            res.setStatusCode(200);
            res.setMessage("register successful");
        }
        return res;
    }
}
