package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("Select * from NOTES where userid = #{userId}")
    List<Note> getAllUserNotes(Integer userId);

    @Select("Select * from NOTES where noteid=#{noteId}")
    Note getNoteById(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) values (#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "noteId")
    int addNote(Note note);

    @Update("Update NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} where noteid=#{noteId}")
    int editNote(Note note);

    @Delete("Delete from NOTES where noteid=#{noteId}")
    int deleteNote(Integer noteId);

}
