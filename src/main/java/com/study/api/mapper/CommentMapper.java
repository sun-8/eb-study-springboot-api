package com.study.api.mapper;

import com.study.api.model.out.comment.CommentListDTO;
import com.study.api.model.process.CommentInfoProcessDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 게시글에 대한 댓글 목록 조회
     * @param seq
     * @return
     */
    List<CommentListDTO> getCommentList(int seq);

    /**
     * 게시글에 대한 댓글 등록
     * @param commentInfoProcessDTO
     * @return
     */
    int registerComment(CommentInfoProcessDTO commentInfoProcessDTO);
}
