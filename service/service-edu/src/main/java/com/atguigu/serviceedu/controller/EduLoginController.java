package com.atguigu.serviceedu.controller;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: hftang
 * @Date: 2020/4/24 17:13
 * @Description:
 */

@CrossOrigin
@Slf4j
@RequestMapping(value = "/eduservice/user")
@RestController
public class EduLoginController {

    @PostMapping("login")
    public R login() {

        return R.ok().data("token", "admin");
    }
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587730360156&di=96ed66742afd9db0db44b1fa5dd9e5df&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
    }


}
