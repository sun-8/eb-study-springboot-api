package com.study.api.service;

import com.study.api.mapper.CommentMapper;
import com.study.api.model.out.CommentOutDTO;
import com.study.api.model.out.comment.CommentListDTO;
import com.study.api.model.process.CommentInfoProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 게시글에 대한 댓글 목록 조회
     * @param key
     * @return
     */
    public CommentOutDTO  getCommentList(int key) {

        List<CommentListDTO> commentList = commentMapper.getCommentList(key);

        CommentOutDTO outDTO = new CommentOutDTO(commentList);
        return outDTO;
    }

    /**
     * 게시글에 대한 댓글 등록
     * @param commentInfoProcessDTO
     * @return
     */
    public int registerComment(CommentInfoProcessDTO commentInfoProcessDTO) {

        int cnt = commentMapper.registerComment(commentInfoProcessDTO);

        return cnt;
    }
}
