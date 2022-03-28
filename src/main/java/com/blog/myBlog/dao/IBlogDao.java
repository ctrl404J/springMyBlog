package com.blog.myBlog.dao;

import com.blog.myBlog.dto.BlogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
// Dao는 DB와의 연결에 필요한 객체를 만들기 위해 만듬.(MyBatis Mapper와 소통)
// Repository역할.
// MyBatis와 인터페이스 메소드의 연결을 위해 @Mapper를 사용.

@Mapper
public interface IBlogDao {

    Integer totalCount(int board_num);
    String searchBoardName(int board_num);

    List<BlogDto> PagingContent(int board_num, int startIndex, int endIndex); // 페이징 처리후의 리스트

    int write(BlogDto blogDto);
    //
    BlogDto contentForm(Integer content_idx);

    // 조회수 증가 구현체
    int hit(int content_idx);


    int contentUpdate(BlogDto blogDto);

    //검색
    List<BlogDto> searchWord(String searchWord, int startIndex, int endIndex);
    int searchCount(String searchWord);

    int deleteAction(int content_idx);
}
