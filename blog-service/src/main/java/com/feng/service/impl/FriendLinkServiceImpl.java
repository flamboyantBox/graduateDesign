package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.feng.pojo.dto.FriendLinkDTO;
import com.feng.pojo.entity.FriendLink;
import com.feng.mapper.FriendLinkMapper;
import com.feng.pojo.vo.ConditionVo;
import com.feng.pojo.vo.FriendLinkVo;
import com.feng.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Override
    public List<FriendLinkDTO> listFriendLinks(ConditionVo conditionVo) {
        // 查询友链列表
        List<FriendLink> friendLinkList = baseMapper.selectList(new LambdaQueryWrapper<FriendLink>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), FriendLink::getLinkName, conditionVo.getKeywords()));

        return friendLinkList.stream().map(item -> {
            FriendLinkDTO friendLinkDTO = new FriendLinkDTO();
            BeanUtils.copyProperties(item, friendLinkDTO);
            return friendLinkDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo) {
        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(friendLinkVo, friendLink);
        this.saveOrUpdate(friendLink);
    }
}
