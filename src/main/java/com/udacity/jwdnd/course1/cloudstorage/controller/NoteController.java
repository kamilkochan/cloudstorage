package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService,UserService userService) {
        this.noteService = noteService;
        this.userService=userService;
    }

    @PostMapping("/addOrUpdateNote")
    public String postNote(Authentication authentication, Note note, Model model){

        String username = authentication.getName();
        note.setUserId(this.userService.getUserId(username));
        int rowsProcessed = this.noteService.addOrUpdateNote(note);

        if (rowsProcessed<0){
            //  return "redirect:result?saveError";
            model.addAttribute("saveNoteSuccess", false);
            model.addAttribute("saveNoteError", true);
        }
        else {
            model.addAttribute("saveNoteSuccess", true);
            model.addAttribute("saveNoteError", false);
            //return "redirect:result?saveSuccess";
        }

        return "result";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer noteid, Model model){
        int rowsProcessed = this.noteService.deleteNote(noteid);

        if (rowsProcessed<0){
            //  return "redirect:result?saveError";
            model.addAttribute("saveNoteSuccess", false);
            model.addAttribute("saveNoteError", true);
        }
        else {
            model.addAttribute("saveNoteSuccess", true);
            model.addAttribute("saveNoteError", false);
        }
        return "result";
    }


}
