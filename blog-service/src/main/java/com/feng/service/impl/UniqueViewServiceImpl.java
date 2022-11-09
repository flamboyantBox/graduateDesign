package com.feng.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.feng.common.constant.RedisPrefixConst;
import com.feng.common.result.ZoneEnum;
import com.feng.common.util.RedisUtils;
import com.feng.pojo.dto.UniqueViewDTO;
import com.feng.pojo.entity.UniqueView;
import com.feng.mapper.UniqueViewMapper;
import com.feng.service.UniqueViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr.feng
 * @since 2022-07-12
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        // 一周的时间
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return baseMapper.listUniqueViews(startTime, endTime);
    }

    @Scheduled(cron = " 0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveUniqueView() {
        // 获取每天用户量
        Long count = redisUtils.sSize(RedisPrefixConst.UNIQUE_VISITOR);
        // 获取昨天日期插入数据
        UniqueView uniqueView = UniqueView.builder()
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone())), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        baseMapper.insert(uniqueView);
    }

    @Scheduled(cron = " 0 1 0 * * ?", zone = "Asia/Shanghai")
    public void clear() {
        // 清空redis访客记录
        redisUtils.del(RedisPrefixConst.UNIQUE_VISITOR);
        // 清空redis游客区域统计
        redisUtils.del(RedisPrefixConst.VISITOR_AREA);
    }
}
