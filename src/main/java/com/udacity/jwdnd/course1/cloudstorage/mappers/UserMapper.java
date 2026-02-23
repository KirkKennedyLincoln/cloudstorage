package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.models.User;

@Mapper()
public interface UserMapper {
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname)\n" + //
                "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    public boolean insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public User getUserByUsername(@Param("username") String username);

    @Select("SELECT userid, username, salt, password, firstname, lastname FROM USERS")
    public User getAllActiveUsers(@Param("username") String username);
}
