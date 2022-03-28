package com.blog.myBlog.dao;

import com.blog.myBlog.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDao {

    List<ReplyDto> replyList(int content_idx);
    int saveReply(ReplyDto replyDto);

    int deleteReply(ReplyDto replyDto);

    int updateReply(ReplyDto replyDto);
}
