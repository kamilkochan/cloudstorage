package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("Select * from CREDENTIALS where userid = #{userId}")
    List<Credential> getAllUserCredentials(Integer userId);

    @Select("Select * from CREDENTIALS where credentialid=#{credentialId}")
    Credential getCredentialById(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) values (#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Update("Update CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} where credentialid=#{credentialId}")
    int editCredential(Credential credential);

    @Delete("Delete from CREDENTIALS where credentialid=#{credentialId}")
    int deleteCredential(Integer credentialId);


}
