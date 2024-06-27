package com.wyj.test.ssm.order.ssm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.Optional;

/**
 * @author wuyingjie
 * Date: 2024/4/17
 */
@Configuration
@EnableStateMachine
@Slf4j
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {


    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.S1)
                .states(EnumSet.allOf(OrderStates.class))
                .state(OrderStates.S1, inAction(), outAction())
                .state(OrderStates.S2, action());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                // 通过事件触发 用 external
                .withExternal()
                .source(OrderStates.S1).target(OrderStates.S2).event(OrderEvents.E1).action(action()).guard(guard())
                .and()
                .withExternal()
                .source(OrderStates.S2).target(OrderStates.S3).event(OrderEvents.E2).guard(guard())
                .and()
                .withExternal()
                .source(OrderStates.S3).target(OrderStates.S1).event(OrderEvents.E3)

                .and()
                .withExternal()
                .source(OrderStates.S1).target(OrderStates.S2).event(OrderEvents.EB).guard(guard())
                .and()
                .withInternal()
                .source(OrderStates.S3).event(OrderEvents.EA).action(action());

    }

    public StateMachineListener<OrderStates, OrderEvents> listener() {
        return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                log.info("listener State change from {} to {}", Optional.ofNullable(from).map(State::getId).orElse(null), to.getId());
            }
        };
    }

    public Action<OrderStates, OrderEvents> action() {
        return new Action<OrderStates, OrderEvents>() {
            @Override
            public void execute(StateContext<OrderStates, OrderEvents> context) {
                log.info("trigger action. event:{}, source:{}. target:{}", context.getEvent().name(), context.getSource().getId(), context.getTarget().getId());
            }
        };
    }

    public Action<OrderStates, OrderEvents> inAction() {
        return new Action<OrderStates, OrderEvents>() {
            @Override
            public void execute(StateContext<OrderStates, OrderEvents> context) {
                log.info("trigger inAction. event:{}, source:{}. target:{}", context.getEvent().name(), context.getSource().getId(), context.getTarget().getId());
            }
        };
    }

    public Action<OrderStates, OrderEvents> outAction() {
        return new Action<OrderStates, OrderEvents>() {
            @Override
            public void execute(StateContext<OrderStates, OrderEvents> context) {
                log.info("trigger outAction. event:{}, source:{}. target:{}", context.getEvent().name(), context.getSource().getId(), context.getTarget().getId());
            }
        };
    }

    public Guard<OrderStates, OrderEvents> guard() {
        return new Guard<OrderStates, OrderEvents>() {
            @Override
            public boolean evaluate(StateContext<OrderStates, OrderEvents> context) {
                if (context.getEvent() == OrderEvents.EB) {
                    log.info("guard filtered. event:{}, source:{}. target:{}", context.getEvent().name(), context.getSource().getId(), context.getTarget().getId());
                    return false;
                }

                log.info("guard allowed. event:{}, source:{}. target:{}", context.getEvent().name(), context.getSource().getId(), context.getTarget().getId());
                return true;
            }
        };
    }

}
