package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.CommentBackDTO;
import com.feng.pojo.dto.CommentDTO;
import com.feng.pojo.dto.ReplyDTO;
import com.feng.pojo.vo.CommentVo;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.ReviewVo;
import com.feng.service.CommentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/comment/core")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping("saveComment")
    public R saveComment(@Valid @RequestBody CommentVo commentVo) {
        commentService.saveComment(commentVo);
        return R.ok();
    }

    @ApiOperation(value = "查询评论")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("listComments")
    public R listComments(Integer articleId) {
        List<CommentDTO> comment = commentService.commentList(articleId);
        return R.ok().data("comment", comment);
    }

    @ApiOperation(value = "查询后台评论")
    @GetMapping("listCommentBackDTO")
    public R listCommentBackDTO(ConditionVo conditionVo) {
        List<CommentBackDTO> commentBackDTOList = commentService.listCommentBackDTO(conditionVo);
        return R.ok().data("commentBackDTOList", commentBackDTOList);
    }

    @ApiOperation(value = "删除评论")
    @DeleteMapping("deleteComments")
    public R deleteComments(@RequestBody List<Integer> commentIdList) {
        commentIdList.forEach(System.out::println);
        commentService.removeByIds(commentIdList);
        return R.ok();
    }

    @ApiOperation(value = "审核评论")
    @PutMapping("updateCommentsReview")
    public R updateCommentsReview(@Valid @RequestBody ReviewVo reviewVo) {
        commentService.updateCommentsReview(reviewVo);
        return R.ok();
    }

    @ApiOperation(value = "查询评论下的回复")
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "Integer")
    @GetMapping("listRepliesByCommentId/{commentId}")
    public R listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        List<ReplyDTO> replyDTOList = commentService.listRepliesByCommentId(commentId);
        return R.ok().data("replayListDTO", replyDTOList);
    }
}

