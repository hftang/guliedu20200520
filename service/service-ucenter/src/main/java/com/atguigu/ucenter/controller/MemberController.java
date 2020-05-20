package com.atguigu.ucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.entity.MemberOrder;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;


    //根据某天获取注册人数
    @PostMapping("/getRegisterCountByDay/{day}")
    public R queryRegistCountByDay(@PathVariable String day){
      Integer num=  this.memberService.queryRegistCountByDay(day);

      return R.ok().data("num",num);
    }

    @PostMapping(value = "/login")
    public R login(@RequestBody Member member) {

        String token = this.memberService.login(member);
        return R.ok().data("token", token);
    }

    //注册
    @PostMapping(value = "regist")
    public R registMember(@RequestBody RegisterVo registerVo) {
        memberService.regist(registerVo);
        return R.ok();
    }

    //获取member信息
    @GetMapping("getMemberInfo")
    public R getMemberInfos(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据memberId 获取用户信息
        Member member = this.memberService.getById(memberId);

        return R.ok().data("userInfo", member);
    }

//    //获取member信息
//    @GetMapping("getMemberInfoApi")
//    public com.atguigu.commonutils.entity.Member getMember(HttpServletRequest request) {
//        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        //根据memberId 获取用户信息
//        Member member = this.memberService.getById(memberId);
//
//        com.atguigu.commonutils.entity.Member member1 = new com.atguigu.commonutils.entity.Member();
//
//        BeanUtils.copyProperties(member,member1);
//
//        return member1;
//    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable String id){

        Member member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        MemberOrder ucenterMemberOrder = new MemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

}

