package com.feng.service;

import com.feng.pojo.dto.CommentBackDTO;
import com.feng.pojo.dto.CommentDTO;
import com.feng.pojo.dto.ReplyDTO;
import com.feng.pojo.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.CommentVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.ReviewVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface CommentService extends IService<Comment> {

    void saveComment(CommentVo commentVo);

    List<CommentDTO> commentList(Integer articleId);

    List<CommentBackDTO> listCommentBackDTO(ConditionVo conditionVo);

    void updateCommentsReview(ReviewVo reviewVo);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);
}
