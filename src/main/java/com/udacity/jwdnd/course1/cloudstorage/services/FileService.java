package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    public int uploadFile(File file){
            return fileMapper.addFile(file);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

    public List<File> getUserFiles(Integer userId){
        return fileMapper.getAllUserFiles(userId);
    }

    public int deleteFile(int fileId){
        return fileMapper.deleteFile(fileId);
    }

    public boolean isFileNameAvailable(String filename) {
        return fileMapper.getFileByName(filename) == null;
    }
}
