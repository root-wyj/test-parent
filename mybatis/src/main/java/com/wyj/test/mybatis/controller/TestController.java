package com.wyj.test.mybatis.controller;

import com.wyj.shorturl.service.ShortUrlService;
import com.wyj.test.mybatis.mapper.UserInfoMapper;
import com.wyj.test.mybatis.model.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/30
 */

@RestController
@RequestMapping("/mybatis/test")
public class TestController {

    @Resource
    UserInfoMapper userInfoMapper;

    @GetMapping("/list/{id}/{count}")
    public List<UserInfo> listUsers(@PathVariable("id") Long id, @PathVariable("count") Integer count) {
        return userInfoMapper.list(id, count);
    }


    @Resource
    ShortUrlService shortUrlService;

    @GetMapping("/shortUrl")
    public String makeShortUrl() {
        return shortUrlService.getShortUrl("http://www.baidu.com");
    }
}
