package com.csye6225.spring2019.controller;


import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.s3.model.Bucket;
import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.entity.Attachment;
import com.csye6225.spring2019.entity.Note;
import com.csye6225.spring2019.facade.NoteFacadeService;
import com.csye6225.spring2019.filter.Verifier;
import com.csye6225.spring2019.service.AttachmentService;
import com.csye6225.spring2019.service.NoteService;
import com.csye6225.spring2019.service.RegisterService;
import com.csye6225.spring2019.util.FileUtil;
import com.csye6225.spring2019.util.S3Util;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;

@Log4j2
@RestController
public class AttachmentController {
    private static final StatsDClient statsDClient = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

    @Autowired
    RegisterService registerService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    NoteService noteService;

    @Autowired
    Environment environment;

    @Autowired
    NoteFacadeService noteFacadeService;

    @GetMapping("/note/{noteId}/attachments")
    public Result<List<Attachment>> getAttachments(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable(value = "noteId") String noteId) throws IOException {
        statsDClient.incrementCounter("endpoint.attachments.http.get");
        log.info("Searching for an attachment");
        Result<List<Attachment>> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if (account == null || !registerService.checkAccount(account)) {
            log.warn("Unauthorized");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
        } else {
            String email = account.getEmailAddress();
            Account user = registerService.findByEmail(email);
            Note note = noteService.getNoteByNoteId(noteId);
            if (note == null) {
                log.warn("Cannot find the note by id : "+noteId);
                res.setStatusCode(404);
                res.setMessage("Not Found");
                httpServletResponse.setStatus(SC_NOT_FOUND);
                httpServletResponse.sendError(SC_NOT_FOUND, "Not Found");
            } else {
                if ((note.getUserId() != user.getId())) {
                    log.warn("The note not belong to the user : " + user.getId());
                    res.setStatusCode(401);
                    res.setMessage("Unauthorized");
                    httpServletResponse.setStatus(SC_UNAUTHORIZED);
                    httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                    return res;
                } else {
                    log.info("Searching for a note successfully");
                    res.setData(attachmentService.findAttachmentsByNoteId(noteId));
                    res.setStatusCode(200);
                    res.setMessage("OK");
                }
            }
        }
        return res;
    }
    @GetMapping("/attachment")
    public Result<List<Attachment>> getA(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Result<List<Attachment>> res = new Result<>();
        String noteId = httpServletRequest.getParameter("noteId");
        res.setData(attachmentService.findAttachmentsByNoteId(noteId));
        res.setStatusCode(200);
        res.setMessage("OK");
        return res;
    }

    @PostMapping("/note/{noteId}/attachments")
    public Result<Attachment> postAttachments(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("noteId") String noteId, @RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        // Basic Auth;
        statsDClient.incrementCounter("endpoint.attachments.http.post");
        log.info("Add a new attachment to the note ");
        Result<Attachment> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        // Exception test
        if (account == null || !registerService.checkAccount(account)) {
            log.warn("Unauthorized");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
            return res;
        } else {
            // check noteId with user
            if (noteService.getNoteByNoteId(noteId) == null) {
                log.warn("Cannot find the note by id : "+noteId);
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST, "Bad Request");
                return res;
            }
            String userEmail = account.getEmailAddress();
            Account user = registerService.findByEmail(userEmail);
            if (user.getId() != noteService.getNoteByNoteId(noteId).getUserId()) {
                httpServletResponse.setStatus(SC_UNAUTHORIZED);
                httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                return res;
            }
            // create Attachment;
            List<Attachment> list = new LinkedList<>();
            Attachment attachment = new Attachment();
            attachment.setNoteId(noteId);
            attachment = setAttachment(multipartFile,attachment);
            if(attachment == null){
                httpServletResponse.setStatus(SC_INTERNAL_SERVER_ERROR);
                httpServletResponse.sendError(SC_INTERNAL_SERVER_ERROR,"Internal_server_error");
                return res;
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            attachment.setUpdateTime(now);
            attachment.setCreateTime(now);
            // add File to DB;
            list.add(attachment);
            attachmentService.addAttachmentsToNote(list);


            // front end
            res.setData(attachment);
            res.setMessage("OK");
            res.setStatusCode(200);
            log.info("add a attachment successfully");
            //file.delete();
        }
        return res;
    }

    @PutMapping("/note/{noteId}/attachments/{idAttachments}")
    public Result<Attachment> updateAttachments(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("noteId") String noteId, @PathVariable("idAttachments") String idAttachment, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        // Basic Auth
        statsDClient.incrementCounter("endpoint.attachments.http.put");
        log.info("Update a attachment");
        Result<Attachment> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        // check account
        if (account == null || !registerService.checkAccount(account)) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
            return res;
        } else {
            // check NoteId exist
            if (noteService.getNoteByNoteId(noteId) == null) {
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST, "Bad Request");
                return res;
            }
            // check Note id belong to user id
            String userEmail = account.getEmailAddress();
            Account user = registerService.findByEmail(userEmail);
            if (user.getId() != noteService.getNoteByNoteId(noteId).getUserId()) {
                log.warn("The note note belong to the user : "+user.getId());
                httpServletResponse.setStatus(SC_UNAUTHORIZED);
                httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                return res;
            }

            // check Attachment or note exist
            Note note = noteService.getNoteByNoteId(noteId);

            Attachment attachment = attachmentService.getAttachmentById(idAttachment);
            if(attachment == null || note==null || !note.getId().equals(attachment.getNoteId())){
                log.warn("Cannot find the note by id " + noteId);
                httpServletResponse.setStatus(SC_NOT_FOUND);
                httpServletResponse.sendError(SC_NOT_FOUND,"Not Found");
                return res;
            }

            // put Attachment
            //attachment = setAttachment(file,attachment);
           /* if(attachment == null){
                httpServletResponse.setStatus(SC_INTERNAL_SERVER_ERROR);
                httpServletResponse.sendError(SC_INTERNAL_SERVER_ERROR,"Internal_server_error");
            }*/
            String multipartFileName = multipartFile.getOriginalFilename();
            String fileName = multipartFileName.substring(0,multipartFileName.lastIndexOf("."))+"-"+System.currentTimeMillis();
            String fileType = multipartFileName.substring(multipartFileName.lastIndexOf(".")+1);
            long fileSize = multipartFile.getSize();
            InputStream inputStream = multipartFile.getInputStream();
            attachment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            noteFacadeService.updateAttachment(inputStream,fileName,fileType,(double)fileSize,idAttachment);
            httpServletResponse.setStatus(SC_NO_CONTENT);
            log.info("Updating a attachment successfully ");
            return res;
        }
    }

    private Attachment setAttachment(MultipartFile multipartFile,Attachment attachment) throws IOException{

        //List<String> name = Splitter.on(".").trimResults().splitToList(multipartFile.getOriginalFilename());
       // String fileType = name.get(1);
        //attachment.setFileType(fileType);
        String multipartFileName = multipartFile.getOriginalFilename();
        String fileName = multipartFileName.substring(0,multipartFileName.lastIndexOf("."))+"-"+System.currentTimeMillis();
        String fileType = multipartFileName.substring(multipartFileName.lastIndexOf(".")+1);
        //transfer  multipart file to file
        //String fileName = name.get(0)+"-"+System.currentTimeMillis();
        attachment.setFileName(fileName);
        attachment.setFileSize(multipartFile.getSize());
        attachment.setFileType(fileType);
        //final File file = File.createTempFile(fileName+"-"+System.currentTimeMillis(),"."+fileType);
        //multipartFile.transferTo(file);
        InputStream inputStream = multipartFile.getInputStream();
        String envType = environment.getProperty("csye6225.save.file.type");
        String url;
        if (envType.equals("local")) {
            url = FileUtil.saveFileToLocal(inputStream, environment.getProperty("csye6225.file.folder"),fileName,fileType);
        }
        // aws file
        else  {
            //Bucket b = S3Util.getBucket(environment.getProperty("csye6225.aws.bucket.name"));
            url = S3Util.uploadFile(environment.getProperty("csye6225.aws.bucket.name"), environment.getProperty("csye6225.file.folder"), inputStream, environment.getProperty("csye6225.aws.url.suffix"),fileName,fileType);
        }
        if(Strings.isNullOrEmpty(url))
            return null;
        attachment.setUrl(url);
        inputStream.close();
        //file.delete();
        return attachment;
    }

    @DeleteMapping("/note/{noteId}/attachments/{idAttachments}")
    public Result<String> deleteAttachments(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @PathVariable("noteId") String noteId, @PathVariable("idAttachments") String idAttachment) throws IOException {
        statsDClient.incrementCounter("endpoint.attachments.http.delete");
        log.info("Start to delete a attachment");
        Result<String> res = new Result<>();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if (account == null || !registerService.checkAccount(account)) {
            log.warn("Unauthorized");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
            return res;
        } else {
            Note note = noteService.getNoteByNoteId(noteId);
            if (note == null) {
                log.warn("Bad note info");
                httpServletResponse.setStatus(SC_BAD_REQUEST);
                httpServletResponse.sendError(SC_BAD_REQUEST, "Bad Request");
                return res;
            }

            String userEmail = account.getEmailAddress();
            Account user = registerService.findByEmail(userEmail);
            if (user.getId() != noteService.getNoteByNoteId(noteId).getUserId()) {
                log.warn("The note not belong to user : "+user.getId());
                httpServletResponse.setStatus(SC_UNAUTHORIZED);
                httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                return res;
            }
            Attachment attachment = attachmentService.getAttachmentById(idAttachment);
            if (attachment == null || !attachment.getNoteId().equals(note.getId()) ) {
                httpServletResponse.setStatus(SC_UNAUTHORIZED);
                httpServletResponse.sendError(SC_UNAUTHORIZED, "Unauthorized");
                return res;
            }
            log.warn("Delete an attachment successfully");
            noteFacadeService.deleteAttachment(idAttachment);
            httpServletResponse.setStatus(SC_NO_CONTENT);
        }
        return res;
    }
}
