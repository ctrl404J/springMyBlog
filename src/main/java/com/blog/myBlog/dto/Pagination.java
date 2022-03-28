package com.blog.myBlog.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class Pagination {

    private int Page = 1;                  //현재 목록의 페이지 번호(Default=1은 처음 열었을때 이페지가 보이도록 한다.)

    private int totalCount;                 //전체 게시물의 갯수

    private int startPage = 1;              //각 페이지블럭 시작번호, 컨트롤단에서 만들?
    private int endPage;                    //각 페이지블럭 끝번호,   컨트롤단에서 만들?

    // 게시물 시작번호, 끝번호
    private int startIndex = 1;              //게시글 시작번호
    private int endIndex = 10;               //게시글 끝번호


}
