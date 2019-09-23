package com.wyj.shorturl.controller;

import com.wyj.shorturl.service.ShortUrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created
 * Author: wyj
 * Date: 2019/9/20
 */
public class ShortUrlController {

    private ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @ResponseBody
    @GetMapping("/s/{shortUrl}")
    public void shortUrl(HttpServletResponse response, @PathVariable("shortUrl")String shortUrl) {
        String url = shortUrlService.getLongUrl(shortUrl);

        if (url != null && url.length() > 0) {
            response.setStatus(302);
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Location", url);
        } else {
            response.setStatus(404);
            response.setContentType("text/html;charset=UTF-8");
//            response.setStatus(302);
//            response.setHeader("Location", getErrorPage());
        }
    }

}
