package com.feng.service.impl;

import com.alibaba.fastjson.JSON;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.exception.Assert;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.entity.Page;
import com.feng.mapper.PageMapper;
import com.feng.pojo.vo.PageVo;
import com.feng.service.PageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 页面 服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<PageVo> listPages() {
        List<PageVo> pageVoList;
        //静态信息放进缓存当中、查找缓存信息，不存在则从mysql读取，更新缓存
        Object pageList = redisUtils.get(RedisPrefixConst.PAGE_COVER);
        if (Objects.nonNull(pageList)){
            pageVoList = JSON.parseObject(pageList.toString(), List.class);
        }else {
            List<Page> pages = baseMapper.selectList(null);
            pageVoList = pages.stream().map(item -> {
                PageVo pageVo = new PageVo();
                BeanUtils.copyProperties(item, pageVo);
                return pageVo;
            }).collect(Collectors.toList());
            redisUtils.set(RedisPrefixConst.PAGE_COVER, JSON.toJSONString(pageVoList));
        }
        return pageVoList;
    }

    @Override
    public void deletePage(Integer pageId) {
        baseMapper.deleteById(pageId);

        //  删除缓存信息
        redisUtils.del(RedisPrefixConst.PAGE_COVER);
    }

    @Override
    public void saveOrUpdatePage(PageVo pageVo) {
        Page page = new Page();
        BeanUtils.copyProperties(pageVo, page);
        this.saveOrUpdate(page);

        // 删除缓存
        redisUtils.del(RedisPrefixConst.PAGE_COVER);
    }
}
