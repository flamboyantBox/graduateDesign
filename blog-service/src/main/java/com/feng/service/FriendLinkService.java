package com.feng.service;

import com.feng.pojo.dto.FriendLinkDTO;
import com.feng.pojo.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.FriendLinkVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks(ConditionVo conditionVo);

    void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo);
}
