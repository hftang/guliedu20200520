package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-05-05
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void regist(RegisterVo registerVo);

    Member getMemberByOpenId(String openid);

    Integer queryRegistCountByDay(String day);
}
