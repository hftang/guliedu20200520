package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hftang
 * @since 2020-05-05
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer queryRegistCountByDay(String day);
}
