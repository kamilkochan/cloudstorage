package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("Select * from FILES where userid = #{userId}")
    List<File> getAllUserFiles(Integer userId);

    @Select("Select * from FILES where fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Select("Select * from FILES where filename = #{filename}")
    File getFileByName(String filename);

    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) values (#{filename},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    int addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    Integer deleteFile(Integer fileId);

}
