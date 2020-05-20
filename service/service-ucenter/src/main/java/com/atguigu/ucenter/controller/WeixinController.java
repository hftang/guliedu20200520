package com.atguigu.ucenter.controller;

/**
 * @Auther: hftang
 * @Date: 2020/5/6 15:14
 * @Description:
 */

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.ucenter.config.ConstantWxUtils;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WeixinController {
    @Autowired
    private MemberService memberService;


    @GetMapping("callback")
    public String callback(String code, String state) {

        log.info("======>经过 code:" + code + "state::" + state);

        //通过一次请求得到临时token
        //1 获取code值，临时票据，类似于验证码
        //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个参数 ：id  秘钥 和 code值
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.APP_ID,
                ConstantWxUtils.APP_SECRET,
                code
        );
        try {
            String oauthToken = HttpClientUtils.get(accessTokenUrl);
            log.info("====oauthToken::" + oauthToken);

            /**
             * {"access_token":"33_msaOIq0ALDoAArXpTeBBBeUdJK_Kl3BHjCSDljAeI_GwVUkeVWQYFGI8DXMgg2LQYjv1ek0CJ5_Y1TQAngaSaCzDstbLEyz2OKiccaayMf8",
             * "expires_in":7200,
             * "refresh_token":"33_kKQTWwZZVU9ql6vgioBA7oPlm6-HeBs5JDrg68k8uyuCY9YCH3NVC8rnHhBP96FTB147hUe3X1nJL1FoQ_nIRB5qKUSpJmvzaPAN7DuYoaM",
             * "openid":"o3_SC5zDRnjlM7un0Flha-WYBAVY",
             * "scope":"snsapi_login",
             * "unionid":
             * "oWgGz1HJjKGbmu1hNNf_OmuSOGHw"
             * }
             */

            Gson gson = new Gson();
            Map map = gson.fromJson(oauthToken, Map.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            String unionid = (String) map.get("unionid");

            //先查询下表中有没有这个用户，有就从表中取出，没有就从微信中取

            Member memberObject = this.memberService.getMemberByOpenId(openid);

            if (memberObject == null) {
                //最后一次请求获取到用户信息

                //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                /**
                 * {"openid":"o3_SC5zDRnjlM7un0Flha-WYBAVY",
                 * "nickname":"hftang","sex":1,
                 * "language":"zh_CN",
                 * "city":"",
                 * "province":"",
                 * "country":"CN",
                 * "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/v1E1xFu5ucoMiaMe9zGVuysiaJ18KNpbmvwJvzFnpZCjjxsiaO32Pdg7kHFKnQtFib4RWpfWFlylLTgrTXwmOzO1fw\/132",
                 * "privilege":[],
                 * "unionid":"oWgGz1HJjKGbmu1hNNf_OmuSOGHw"}
                 */

                log.info("useifon {}", userInfo);
                //获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");//昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像
                String openid02 = (String) userInfoMap.get("openid");//头像
//                String sex = (String) userInfoMap.get("sex");//头像

                memberObject = new Member();
                memberObject.setNickname(nickname);
                memberObject.setAvatar(headimgurl);
                memberObject.setOpenid(openid02);

                //保存到表中
                memberService.save(memberObject);
            }

            //最后 将 member jwt后给前端
            String jwtToken = JwtUtils.getJwtToken(memberObject.getId(), memberObject.getNickname());

            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (Exception e) {
            e.printStackTrace();

            throw new GuliException(20001, "微信登录失败");
        }


    }

    @GetMapping("login")
    public String login() {

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"
                + "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对 redirectUrl 进行加密
        String redirectUrl = ConstantWxUtils.REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String format = String.format(baseUrl, ConstantWxUtils.APP_ID, redirectUrl, "atguigu");


        return "redirect:" + format;
    }
}
