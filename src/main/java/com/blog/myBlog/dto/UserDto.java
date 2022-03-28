package com.blog.myBlog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private int user_idx;
    private String user_id;
    private String user_name;
    private String user_pass;
    private String user_q;
    private String user_a;
    private int user_content_idx;

}
