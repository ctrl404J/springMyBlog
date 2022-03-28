package com.blog.myBlog.dao;

import com.blog.myBlog.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    int joinAction(UserDto userDto);
    UserDto idCheck(String user_id);

    String findPass(UserDto userDto);
}
