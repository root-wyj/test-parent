package com.wyj.test.aop.config.request;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/19
 */

//@RestController
public class ShortUrlController {

    @ResponseBody
    @GetMapping("/s/{shortUrl}")
    public String shortUrl(@PathVariable("shortUrl")String shortUrl) {
        return shortUrl;
    }


}
