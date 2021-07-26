package com.wyj.test.sentinel.app.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wyj.test.sentinel.app.feign.fallback.WeatherClientFallback;

/**
 * 数据来源于 https://www.sojson.com/blog/305.html
 * Created on 2020-11-02
 */
//@FeignClient(name = "WeatherClient", url = "http://t.weather.itboy.net", fallbackFactory = WeatherClientFallback.class)
@FeignClient(name = "WeatherClient", url = "http://weather.itboy.net", fallbackFactory = WeatherClientFallback.class)
public interface WeatherClient {

    // 天津 101030100
    // 北京 101010100
    // cityCode https://github.com/baichengzhou/weather.api/blob/master/src/main/resources/citycode-2019-08-23.json
    @GetMapping("/api/weather/city/{cityCode}")
    Map<String, Object> weather(@PathVariable("cityCode") String cityCode);

}
