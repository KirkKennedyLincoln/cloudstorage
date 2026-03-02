package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;

    public CredentialService(
        CredentialMapper credentialMapper,
        UserMapper userMapper
    ) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
    }

    public Boolean addCredentials(Credential credential, String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        credential.setUserid(userId);
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
        return this.credentialMapper.updateCredentials(credential);
    }

    public Credential[] getAllCredentials(String username) {
        Integer userId = this.userMapper.getUserIdByUsername(username);
        return this.credentialMapper.retrieveAllCredentials(userId);
    }
}
