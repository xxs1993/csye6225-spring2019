package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.entity.Note;
import com.csye6225.spring2019.filter.Verifier;
import com.csye6225.spring2019.service.NoteService;
import com.csye6225.spring2019.service.RegisterService;
import com.google.common.base.Strings;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@RestController
public class NoteController {
    private static final StatsDClient statsDClient = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);
    @Autowired
    NoteService noteService;
    @Autowired
    RegisterService registerService;



    @GetMapping("/note")
    public Result<List<Note>> getAllNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        statsDClient.incrementCounter("endpoint.note.http.get");
        log.info("Get the notes");
        Result<List<Note>> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            log.warn("Invalid account info ");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
            return res;
        }
        else{
            account = registerService.findByEmail(account.getEmailAddress());
            int id = account.getId();
            res.setData(noteService.findAll(id));
            res.setStatusCode(200);
            res.setMessage("OK");
            log.info("Get your notes successfully");
        }
        return res;
    }

    @PostMapping("/note")
    public Result<Note> postNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody Note note) throws IOException{
        statsDClient.incrementCounter("endpoint.note.http.post");
        log.info("upload a note ");
        Result<Note> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            log.warn("Invalid account info ");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
            return res;
        }
        else{
            if(Strings.isNullOrEmpty(note.getContent()) || Strings.isNullOrEmpty(note.getTitle())){
                log.warn("Invalid note info");
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST,"Bad Request");
                return res;
            }
           // httpServletResponse.se(SC_CREATED,"created");
            account = registerService.findByEmail(account.getEmailAddress());
            note.setUserId(account.getId());
            note = noteService.addNewNote(note);
            if(note == null){
                log.error("Add note failed ");
                httpServletResponse.sendError(SC_INTERNAL_SERVER_ERROR,"Unexpected error");
                return res;
            }
//            httpServletResponse.setStatus(SC_CREATED);*/
            if(note.getAttachments() == null){
                note.setAttachments(new ArrayList<>());
            }
            httpServletResponse.setStatus(SC_CREATED);
            //httpServletResponse.sendError(SC_CREATED,"Created");
            res.setData(note);
            res.setStatusCode(201);
            res.setMessage("created");
            log.info("Add a note successfully");
        }
        return res;
    }


    @GetMapping("/note/{id}")
    public Result<Note> getCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable(name = "id") String noteId) throws IOException{
        statsDClient.incrementCounter("endpoint.note.http.get");
        log.info("Find a note by id : "+noteId);
        Result<Note> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            log.warn("Invalid account info ");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
            return res;
        }
        else{
            String email = account.getEmailAddress();
            Account user = registerService.findByEmail(email);
            Note note = noteService.getNoteByNoteId(noteId);
            if(note == null){
                log.warn("Cannot find the note by id : "+noteId);
                res.setStatusCode(404);
                res.setMessage("Not Fount");
                httpServletResponse.setStatus(SC_NOT_FOUND);
                httpServletResponse.sendError(SC_NOT_FOUND,"Not Found");
                return res;
            }
            else{
                if((note.getUserId() != user.getId())){
                    log.warn("Unauthorized");
                    res.setStatusCode(401);
                    res.setMessage("Unauthorized");
                    httpServletResponse.setStatus(SC_UNAUTHORIZED);
                    httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
                    return res;
                }else{
                    log.info("Successfully find a note");
                    res.setData(noteService.getNoteByNoteId(noteId));
                    res.setStatusCode(200);
                    res.setMessage("OK");
                }
            }
        }
        return res;
    }

    @PutMapping("/note/{id}")
    public Result<Note> putCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable(name = "id") String noteId, @RequestBody Note note) throws IOException{
        statsDClient.incrementCounter("endpoint.note.http.put");
        log.info("Update a note");
        Result<Note> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            log.warn("Invalid account info");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
            return res;
        }
        else{
            String content = note.getContent();
            String title = note.getTitle();
            Note userNote = noteService.getNoteByNoteId(noteId);
            if(userNote == null || Strings.isNullOrEmpty(note.getTitle()) || Strings.isNullOrEmpty(note.getContent())){
                log.warn("Invalid note info");
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST,"Bad Request");
                return res;
            }
            else {
                userNote.setContent(content);
                userNote.setTitle(title);
                String email = account.getEmailAddress();
                Account user = registerService.findByEmail(email);
                if ((userNote.getUserId() != user.getId())) {
                    log.warn("The note not belong to the user : "+user.getId());
                    httpServletResponse.setStatus(SC_UNAUTHORIZED);
                    httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                    return res;
                } else {
                    log.info("Updating note successfully");
                    httpServletResponse.setStatus(SC_NO_CONTENT);
                    noteService.updateNote(userNote);
                    res.setData(userNote);
                    res.setMessage("OK");
                }
            }
        }
        return res;
    }

    @DeleteMapping("/note/{id}")
    public Result<String> deleteCertainNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable(name = "id") String noteId) throws IOException {
        statsDClient.incrementCounter("endpoint.note.http.delete");
        Result<String> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null || !registerService.checkAccount(account)){
            log.warn("Unauthorized");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Unauthorized");
            return res;
        }
        else{
            String email = account.getEmailAddress();
            Account user = registerService.findByEmail(email);
            Note note = noteService.getNoteByNoteId(noteId);
            if(note == null){
                log.warn("Cannot find the note by id : "+noteId);
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST,"Bad Request");
                return res;
            }
            else {
                if ((note.getUserId() != user.getId())) {
                    log.warn("Unauthorized");
                    httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                } else {
                    log.info("Delete the note successfully");
                    noteService.deleteNoteByNoteId(noteId);
                    httpServletResponse.setStatus(SC_NO_CONTENT);
                    res.setMessage("successfully deleted");
                }
            }
        }
        return res;
    }
}
