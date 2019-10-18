package com.wyj.test.spi.imp;

import com.wyj.test.spi.SPIService;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/18
 */
public class SPIServiceImp1 implements SPIService {
    @Override
    public void execute() {
        System.out.println(this.getClass().getName());
    }
}
