package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;

    public CredentialService(
        CredentialMapper credentialMapper,
        UserMapper userMapper, 
        EncryptionService encryptionService
    ) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public Boolean addCredentials(Credential credential, String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        credential.setUserid(userId);
        credential.setPassword(
            this.encryptionService.encryptValue(
                credential.getPassword(), 
                credential.getCredentialkey()
            )
        );
        return this.credentialMapper.storeCredentials(credential);
    }

    public Credential getCredentialsByUrl(String url, String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        return this.credentialMapper.retrieveCredentials(url, userId);
    }

    public Boolean deleteCredential(String url, String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        return this.credentialMapper.deleteCredentials(url, userId);
    }

    public Boolean updateCredential(Credential credential, String username) {
        credential.setPassword(
            this.encryptionService.encryptValue(
                credential.getPassword(),
                credential.getCredentialkey()
            )
        );
        return this.credentialMapper.updateCredentials(credential);
    }

    public Credential[] getAllCredentials(String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        return this.credentialMapper.retrieveAllCredentials(userId);
    }

    public String encryptPassword(String password, String key) {
        return this.encryptionService.encryptValue(password, key);
    }

    public String decryptPassword(String password, String key) {
        return this.encryptionService.decryptValue(password, key);
    }
}
