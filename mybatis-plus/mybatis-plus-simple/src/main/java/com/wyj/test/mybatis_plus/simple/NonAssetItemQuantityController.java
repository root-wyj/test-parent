package com.wyj.test.mybatis_plus.simple;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author wuyingjie
 * Created on 2020-02-19
 */
@RestController
@RequestMapping("/nonasset")
public class NonAssetItemQuantityController {


    @Resource
    private NonAssetItemQuantityMapper nonAssetItemQuantityMapper;
    @Resource
    private NonAssetItemQuantityService nonAssetItemQuantityService;

    @GetMapping("/save")
    public String save() {
        NonAssetItemQuantity nonAssetItemQuantity = new NonAssetItemQuantity();
        nonAssetItemQuantity.setItemId(1);
        nonAssetItemQuantity.setBusinessType("businessType");
        nonAssetItemQuantity.setCityName("cityName");
        nonAssetItemQuantity.setLocationName("localtionName");
        nonAssetItemQuantity.setItemType("itemType");
        nonAssetItemQuantity.setOnHandAmount(10);
        nonAssetItemQuantity.setCreateCode("createCode");
        nonAssetItemQuantity.setUpdateCode("updateCode");
        nonAssetItemQuantity.setCreateTime(new Date());
        nonAssetItemQuantityMapper.insert(nonAssetItemQuantity);
        return "save";
    }

    @GetMapping("/get")
    public String get() {
        System.out.println(nonAssetItemQuantityMapper.selectById(1));
        return "get";
    }

    @GetMapping("/list")
    public String list() {
        System.out.println(nonAssetItemQuantityMapper.selectList(null));
        return "list";
    }

    @GetMapping("/page")
    public String pageList() {
        Page<NonAssetItemQuantity> page = new Page<>(1, 1);
        QueryWrapper<NonAssetItemQuantity> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("cityName", "city");
        nonAssetItemQuantityService.page(page);
        System.out.println(page);
        return "page";
    }

    public static void main(String[] args) {
        Integer i = 1;
        Integer j = 3;
        System.out.println(i.compareTo(j));
    }

}
