package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    //获取所有的banner
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @GetMapping(value = "getBanners")
    public R getBannerLists() {
        List<CrmBanner> list = this.crmBannerService.getListBanners();
        return R.ok().data("items", list);
    }

}

