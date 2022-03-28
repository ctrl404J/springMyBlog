package com.blog.myBlog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;
// DTO는 UI로 뿌려줄 때 필요한 클래스

@Getter @Setter
@NoArgsConstructor
@ToString
@Component
public class BlogDto {
    // Board
    private int content_idx;
    private int board_num;
    private String writer_name;
    private String board_title;
    private String board_content;
    private Date board_date;
    private int board_hit;

}
