package com.atguigu.ucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import feign.QueryMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-05-05
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(Member member) {

        if (member == null) {
            throw new GuliException(20001, "账户不能为空");
        }

        String mobile = member.getMobile();

        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "账户或密码为空");
        }

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Member member1 = this.baseMapper.selectOne(queryWrapper);

        if (member1 == null) {
            throw new GuliException(20001, "账户不存在");
        }

        String pwd02 = MD5.encrypt(password);
        if (!pwd02.equals(member1.getPassword())) {
            throw new GuliException(20001, "密码不正确");
        }

        //判断是否被禁用
        if (member1.getIsDisabled()) {
            throw new GuliException(20001, "被禁用了");
        }

        //登录成功 返回token
        String jwtToken = JwtUtils.getJwtToken(member1.getId(), member1.getNickname());

        return jwtToken;
    }

    //注册

    @Override
    public void regist(RegisterVo registerVo) {

        if (registerVo == null) {
            throw new GuliException(20001, "注册对象不能为空");
        }

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "注册对象字段不能为空");
        }

        //查一下code 是否过期
        String code02 = redisTemplate.opsForValue().get(mobile);

        if (StringUtils.isEmpty(code02)) {
            throw new GuliException(20001, "验证码过期");
        }

        if (!code.equals(code02)) {
            throw new GuliException(20001, "验证码不对");
        }

        //查询下 用户是否重复

        QueryWrapper<Member> wapper = new QueryWrapper<Member>();
        wapper.eq("mobile", mobile);
        Integer selectCount = baseMapper.selectCount(wapper);

        if (selectCount > 0) {
            throw new GuliException(20001, "用户已存在");
        }

        Member member = new Member();

        member.setIsDisabled(false);
        member.setAvatar("https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1130951545,1692830597&fm=26&gp=0.jpg");
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));

        baseMapper.insert(member);


    }

    /***
     * 根据 openid
     * 查询
     * @param openid
     * @return
     */

    @Override
    public Member getMemberByOpenId(String openid) {

        QueryWrapper<Member> wapper = new QueryWrapper<>();
        wapper.eq("openid", openid);
        Member member = this.baseMapper.selectOne(wapper);


        return member;
    }

    /**
     * 获取注册人数
     *
     * @param day
     * @return
     */

    @Override
    public Integer queryRegistCountByDay(String day) {

        Integer num = this.baseMapper.queryRegistCountByDay(day);

        return num;
    }
}
