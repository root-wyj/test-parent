package com.wyj.test.ssm;

import com.wyj.test.ssm.order.ssm.OrderEvents;
import com.wyj.test.ssm.order.ssm.OrderStates;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuyingjie
 * Date: 2024/4/17
 */
@RestController
@RequestMapping("/sms")
public class TestController {

    @Autowired
    private StateMachine<OrderStates, OrderEvents> stateMachine;

    @GetMapping("/t1")
    public String t1(@RequestParam(value = "p1", required = false) String p1) {

        return "这是参数:" + p1;
    }

    @GetMapping("/event/{event}")
    public String event(@PathVariable("event") String event) {
        OrderEvents orderEvent = OrderEvents.valueOf(Strings.toRootUpperCase(event));
        stateMachine.sendEvent(orderEvent);
        return "success:" + orderEvent;
    }
}
