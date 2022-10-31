package com.feng.controller;


import com.feng.common.result.R;
import com.feng.pojo.dto.FriendLinkDTO;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.FriendLinkVo;
import com.feng.service.FriendLinkService;
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
@RequestMapping("/friendLink/core")
public class FriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;

    @ApiOperation(value = "查看友链列表")
    @GetMapping("/linkList")
    public R listFriendLinks(ConditionVo condition) {
        List<FriendLinkDTO> friendLinkDTOList = friendLinkService.listFriendLinks(condition);
        return R.ok().data("list", friendLinkDTOList);
    }

    @ApiOperation(value = "保存或修改友链")
    @PostMapping("saveOrUpdateFriendLink")
    public R saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVo friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return R.ok();
    }

    @ApiOperation(value = "删除友链")
    @DeleteMapping("removeFriendLink")
    public R removeFriendLink(@RequestBody List<Integer> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return R.ok();
    }
}

