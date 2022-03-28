package com.blog.myBlog.controller;

import com.blog.myBlog.dao.ReplyDao;
import com.blog.myBlog.dto.BlogDto;
import com.blog.myBlog.dto.ReplyDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;


@Controller
@Slf4j
@ComponentScan({"com.blog.myBlog.dto"})
public class ReplyController {

    @Autowired
    ReplyDao replyDao;

    @Autowired
    BlogDto blogDto;

    @GetMapping("replyList")
    @ResponseBody
    public Map<String, Object> replyList(int content_idx) throws IOException {

        //log.info("글번호:{}", content_idx);
        List<ReplyDto> replyList = replyDao.replyList(content_idx);
        Map<String, Object> rs = new HashMap<>();

        log.info("댓글정보:{}", replyList);
            rs.put("msg", String.format("댓글 %d개", replyList.size()));
            rs.put("replyList", replyList);
            return rs;
    }

    // 댓글 저장.
    @PostMapping("saveReply")
    @ResponseBody
    public int saveReply(ReplyDto replyDto, Map map) {
        log.info("데이터?:{}", replyDto);
        //log.info("내용?:{}", reply_content);

        int result = replyDao.saveReply(replyDto);
        log.info("결과:{}", result);

        return result;
    }

    // 댓글 삭제
    @PostMapping("deleteReply")
    @ResponseBody
    public int deleteReply(ReplyDto replyDto){
        log.info("삭제할 댓글번호:{}", replyDto);
        int result = replyDao.deleteReply(replyDto);
        return result;
    }

    // 댓글 수정
    @PostMapping("updateReply")
    @ResponseBody
    public int updateReply(ReplyDto replyDto){
        int result = replyDao.updateReply(replyDto);
        log.info("넘어온 수정값:{}", replyDto);
        log.info("수정한 결과값:{}", result);

        return result;
    }


}
