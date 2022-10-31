package com.feng.mapper;

import com.feng.pojo.dto.CommentBackDTO;
import com.feng.pojo.dto.CommentDTO;
import com.feng.pojo.dto.ReplyCountDTO;
import com.feng.pojo.dto.ReplyDTO;
import com.feng.pojo.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.pojo.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentBackDTO> listCommentBackDTO(@Param("conditionVo") ConditionVo conditionVo);

    List<CommentDTO> commentList(@Param("articleId") Integer articleId);

    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList, @Param("articleId") Integer articleId);

    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    List<ReplyDTO> listRepliesByCommentId(@Param("commentId") Integer commentId);
}
