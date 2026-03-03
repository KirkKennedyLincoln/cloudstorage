package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

@Mapper()
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, credentialkey, password, userid) " +
        "VALUES (#{url}, #{username}, #{credentialkey}, #{password}, #{userid})")
    public Boolean storeCredentials(Credential credential);

    @Select("SELECT credentialid, url, username, credentialkey, password, userid FROM CREDENTIALS WHERE url = #{url} AND userid = #{userId}")
    public Credential retrieveCredentials(String url, Integer userId);

    @Select("SELECT credentialkey FROM CREDENTIALS WHERE url = #{url} AND userid = #{userId}")
    public String retrieveCredentialKey(String url, Integer userId);

    @Select("SELECT credentialid, url, username, credentialkey, password FROM CREDENTIALS WHERE userid = #{userId}")
    public Credential[] retrieveAllCredentials(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE url = #{url} AND userid = #{userId}")
    public Boolean deleteCredentials(String url, Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{credential.url}, username = #{credential.username}, credentialkey = #{credential.credentialkey}, password = #{credential.password} WHERE credentialid = #{credential.credentialid}")
    public Boolean updateCredentials(@Param("credential") Credential credential);
}
