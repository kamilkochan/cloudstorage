package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController implements HandlerExceptionResolver {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model)  throws IOException {
        String username = authentication.getName();
        ModelAndView modelAndView = new ModelAndView("result");

            File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), this.userService.getUserId(username), file.getBytes());

        if(file.isEmpty()){
            model.addAttribute("error","File not found!");
        }
        else if (fileService.isFileNameAvailable(newFile.getFilename())==false){
            model.addAttribute("error","It is not possible to upload two files with the same name!");
        }

        else {
                int rowsProcessed = fileService.uploadFile(newFile);
                if (rowsProcessed<0){
                    model.addAttribute("saveFileSuccess", false);
                    model.addAttribute("saveFileError", true);
                }
                else {
                    model.addAttribute("saveFileSuccess", true);
                    model.addAttribute("saveFileError", false);
                }
            }

        return "result";
    }

    @GetMapping("/viewFile")
    public ResponseEntity<Resource> fileDownload(@RequestParam("fileid") Integer fileId, Model model) {
        File currFile = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + currFile.getFilename() + "\"")
                .body(new ByteArrayResource(currFile.getFileData()));
    }

    @GetMapping("/deleteFile")
    public String fileDelete(Model model, @RequestParam Integer fileid) {
        int rowsProcessed = fileService.deleteFile(fileid);
        model.addAttribute("success", true);
        if (rowsProcessed<0){
            model.addAttribute("saveFileSuccess", false);
            model.addAttribute("saveFileError", true);
        }
        else {
            model.addAttribute("saveFileSuccess", true);
            model.addAttribute("saveFileError", false);
        }
        return "result";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView("result.html");
        if (e instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("error", "File size exceeds limit!");
        }
        return modelAndView;
    }
}
