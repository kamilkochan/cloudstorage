package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper,EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }


    public int addOrUpdateCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        if(credentialMapper.getCredentialById(credential.getCredentialId())==null){

            return credentialMapper.addCredential(new Credential(null,credential.getUrl(),credential.getUsername(),encodedKey,encryptedPassword,credential.getUserId()));
        }
        else return credentialMapper.editCredential(new Credential(credential.getCredentialId(),credential.getUrl(),credential.getUsername(),encodedKey,encryptedPassword,credential.getUserId()));
    }

    public Credential getCredentialById(Integer credentialId){
        return credentialMapper.getCredentialById(credentialId);
    }


    public int deleteCredential(int credentialID){
        return credentialMapper.deleteCredential(credentialID);
    }


    public List<Credential> getUserCredentials(Integer userId){
        return credentialMapper.getAllUserCredentials(userId);
    }





}
