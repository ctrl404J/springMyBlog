package com.blog.myBlog.controller;

import com.blog.myBlog.dao.IBlogDao;
import com.blog.myBlog.dto.BlogDto;
import com.blog.myBlog.dto.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class MyController {

    @Autowired
    IBlogDao blogDao;

    @GetMapping("/")
    public String mainPage(){
        return "layouts/index";
    }

    // 게시판 글목록 조회
    @GetMapping("conList")
    public String conList(BlogDto blogDto, Map model, Pagination pagination) {

        //--------------------------------------------------------------------------------
        String boardName = blogDao.searchBoardName(blogDto.getBoard_num());   // 게시판 이름
        int totalCount = blogDao.totalCount(blogDto.getBoard_num());          // 게시글 총글수
        //--------------------------------------------------------------------------------
        //--------------- 현재페이지 번호, 현재 시작게시물 번호, 끝게시물 번호 구하고---------------

        int startIndex = (pagination.getPage() - 1) * 10 + 1;  //게시글 시작번호
        int endIndex = startIndex + (10 - 1);                  //게시글 끝번호
        pagination.setStartIndex(startIndex);
        pagination.setEndIndex(endIndex);

        int board_num = blogDto.getBoard_num();                //보드넘버
        // ---------------------------------페이지블럭---------------------------------------

        int page = pagination.getPage();      //현재 페이지
        int startPage = page -(page-1)%5;     //블럭당 시작 페이지
        int endPage = (int) Math.ceil((double)totalCount/10);          //블럭당 끝페이지번호, 총페이지수
        // --------------------------------------------------------------------------------

//        log.info("토탈카운트:{}", totalCount);
//        log.info("현재페이지Page:{}", pagination.getPage()); // 현재페이지 출력
//        log.info("게시물 시작번호:{}", startIndex);
//        log.info("게시물 마감번호:{}", endIndex);
//
//        log.info("페이지 시작번호:{}", startPage);  //페이지 시작번호
//        log.info("페이지 마감번호:{}", endPage);    // 총 페이지 출력


        //------------------------- 찾아온 게시글 넘김 -----------------------------------------
        List<BlogDto> contentList = blogDao.PagingContent(board_num, startIndex, endIndex);


        //--------------- model에 셋팅까지 ------------------
        model.put("boardName", boardName);
        model.put("totalCount", totalCount);

        model.put("page", page);
        model.put("startPage", startPage);
        model.put("endPage", endPage);
        model.put("list", contentList);

        return "layouts/conList";

    }

    // 글쓰기 Form 이동.
    @GetMapping("writeForm")
    public String writeForm(){

        return "layouts/writeForm"; //writeForm을 디스패치 함.
    }
    // 글쓰기 액션.
    @PostMapping("writeForm")
    @ResponseBody
    public String writeAction(BlogDto blogDto){
        int result = blogDao.write(blogDto);
        log.info("결과={}", result);
        //redirectAttributes.addAttribute("status", true);

        if(result == 1){
            // 글쓰기 성공하면 새로운글이 보이는 1페이지로 넘어가도록 수정이 필요.
            return "<script>alert('글쓰기 성공!'); location.href='javascript:history.go(-2)'; </script>;";
        }else{
            return "<script>alert('글쓰기 실패!'); location.href='javascript:history.go(-1)'; </script>;";
        }

    }

    // 게시글 상세정보
    @GetMapping("contentForm")
    public String contentForm(@RequestParam("content_idx") int content_idx, Model model){

        BlogDto blog = blogDao.contentForm(content_idx);
        model.addAttribute("list",blog);
        log.info("게시글정보={}", blog);
        // 조회수 증가
        blogDao.hit(content_idx);

        // 댓글 조회
//        List<ReplyDto> replyList = replyDao.replyList(content_idx);
//        model.addAttribute("replyList", replyList);
//
//
//        log.info("댓글정보={}", replyList);

        return "layouts/contentForm";
    }

    // 글수정 이동
    @GetMapping("updateForm/{content_idx}")
    public String updateForm(@PathVariable("content_idx") int content_idx, Model model){
        log.info("넘어온 파라미터:{}", content_idx);
        BlogDto blog = blogDao.contentForm(content_idx);
        model.addAttribute("list", blog);
        return "layouts/updateForm";
    }

    // 글수정 액션
    @PostMapping("/updateAction")
    @ResponseBody
    public String updateAction(@ModelAttribute BlogDto blogDto){ //@ModelAttribute는 넘어온 파라미터를 DTO에 셋팅까지 해준다.

        int result = blogDao.contentUpdate(blogDto);
        log.info("수정결과:{}", result);

        if(result == 1){
            return "<script>alert('글수정 성공!'); location.href='javascript:history.go(-3)'; </script>;";
        }else{
            return "<script>alert('글수정 실패!'); location.href='javascript:history.go(-1)'; </script>;";
        }
    }

    // 글삭제
    @PostMapping("deleteAction")
    @ResponseBody
    public int deleteAction(int content_idx){
        log.info("삭제글번호:{}", content_idx);
        int result = blogDao.deleteAction(content_idx);
        return result;

    }

    // 검색
    @GetMapping("searchWord")
    public String searchWord(@RequestParam("searchWord") String searchWord, BlogDto blogDto, Pagination pagination, Map model){


        int totalCount = blogDao.searchCount(searchWord);        //게시글 총글수
        int startIndex = (pagination.getPage() - 1) * 10 + 1;               //게시글 시작번호
        int endIndex = startIndex + (10 - 1);                               //게시글 끝번호
        pagination.setStartIndex(startIndex);
        pagination.setEndIndex(endIndex);
        List<BlogDto> searchList = blogDao.searchWord(searchWord , startIndex, endIndex);
        log.info("검색결과={}", searchList);
        int page = pagination.getPage();                                    //현재 페이지
        int startPage = page -(page-1)%5;                                   //블럭당 시작 페이지
        int endPage = (int) Math.ceil((double)totalCount/10);               //블럭당 끝페이지번호, 총페이지수

        // -- info --
        log.info("토탈카운트:{}", totalCount);
        log.info("현재페이지Page:{}", pagination.getPage()); // 현재페이지 출력
        log.info("게시물 시작번호:{}", startIndex);
        log.info("게시물 마감번호:{}", endIndex);

        log.info("페이지 시작번호:{}", startPage);  //페이지 시작번호
        log.info("페이지 마감번호:{}", endPage);    // 총 페이지 출력
        // -- ---- --

        model.put("searchWord", searchWord);
        log.info("검색어:{}", searchWord);
        model.put("totalCount", totalCount);
        model.put("page", page);
        model.put("startPage", startPage);
        model.put("endPage", endPage);
        model.put("list", searchList);

        return "layouts/searchList";
    }


}
