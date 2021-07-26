package com.wyj.test.sentinel.app.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2020-12-02
 */
@Service
@Slf4j
public class FakeService {

    @SentinelResource(blockHandler = "blockHandler", fallback = "fallback")
    public String degradeWithException(int point) {
        Random random = new Random();
        if (random.nextInt(100) < point) {
            throw new RuntimeException("thr e");
        }
        return "success";
    }

    public String blockHandler(int point, BlockException e) {
        log.error("block. point:{}, e:{}", point, e);
        return "block";
    }

    public String fallback(int point, Throwable e) {
        log.error("fallback. point:{}, e:{}", point, e);
        return "fallback";
    }
}
