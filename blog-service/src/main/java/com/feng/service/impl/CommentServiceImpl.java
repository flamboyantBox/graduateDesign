package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.util.HTMLUtils;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.dto.CommentBackDTO;
import com.feng.pojo.dto.CommentDTO;
import com.feng.pojo.dto.ReplyCountDTO;
import com.feng.pojo.dto.ReplyDTO;
import com.feng.pojo.entity.Comment;
import com.feng.mapper.CommentMapper;
import com.feng.pojo.vo.CommentVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.ReviewVo;
import com.feng.pojo.vo.WebsiteConfigVo;
import com.feng.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.service.WebsiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void saveComment(CommentVo commentVo) {
        // 判断网站是否需要审核
        WebsiteConfigVo websiteConfig = websiteConfigService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();

        // 过滤标签
        commentVo.setCommentContent(HTMLUtils.deleteTag(commentVo.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(commentVo.getUserInfoId())
                .replyUserId(commentVo.getReplyUserId())
                .articleId(commentVo.getArticleId())
                .commentContent(commentVo.getCommentContent())
                .parentId(commentVo.getParentId())
                .review(isReview == 1)
                .build();
        this.save(comment);
        // 判断是否开启邮箱通知,通知用户 TODO
        if (websiteConfig.getIsEmailNotice().equals(1)) {
            notice(comment);
        }
    }

    @Override
    public List<CommentDTO> commentList(Integer articleId) {
        // 查询文章评论量
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getArticleId, articleId)
                .isNotNull(Comment::getParentId)
                .eq(Comment::getReview, 1));

        // 分页查询评论集合
        List<CommentDTO> commentDTOList = baseMapper.commentList(articleId);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisUtils.hGetAll(RedisPrefixConst.COMMENT_LIKE_COUNT);

        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据(只展示四条数据，其他数据需要展开)
        List<ReplyDTO> replyDTOList = baseMapper.listReplies(commentIdList, articleId);
        // 封装回复点赞量
//        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = baseMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
//            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });

        return commentDTOList;
    }

    @Override
    public List<CommentBackDTO> listCommentBackDTO(ConditionVo conditionVo) {
        return baseMapper.listCommentBackDTO(conditionVo);
    }

    @Override
    public void updateCommentsReview(ReviewVo reviewVo) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVo.getIdList().stream().map(item ->
                Comment.builder()
                        .id(item)
                        .review(reviewVo.getIsReview() == 1)
                        .build()
        ).collect(Collectors.toList());

        this.updateBatchById(commentList);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        // 查询父id所有回复信息
        return baseMapper.listRepliesByCommentId(commentId);
    }

    /**
     * 通知评论用户
     *  TODO
     * @param comment 评论信息
     */
    @Async
    public void notice(Comment comment) {

    }
}
