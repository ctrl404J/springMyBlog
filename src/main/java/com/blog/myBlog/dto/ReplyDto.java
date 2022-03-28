package com.blog.myBlog.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@ToString
public class ReplyDto {
    // Reply
    private int reply_idx;
    private String reply_name;
    private String reply_content;
    private Date reply_date;
    private int reply_board_idx;

}
