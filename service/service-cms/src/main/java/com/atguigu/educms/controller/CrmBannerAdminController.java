package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p> 给后台使用的
 *
 * @author hftang
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询
    @GetMapping(value = "pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> page = new Page<CrmBanner>(current, limit);

        IPage<CrmBanner> bannerIPage = this.crmBannerService.page(page, null);

        return R.ok().data("items", bannerIPage.getRecords()).data("total", bannerIPage.getTotal());

    }

    //添加banner

    @PostMapping(value = "addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        this.crmBannerService.save(crmBanner);
        return R.ok();
    }

    //修改banner


    @PostMapping(value = "updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        this.crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    //删除
    @DeleteMapping(value = "deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        this.crmBannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }
}

