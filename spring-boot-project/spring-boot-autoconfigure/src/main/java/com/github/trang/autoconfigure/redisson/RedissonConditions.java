package com.github.trang.autoconfigure.redisson;

import static org.springframework.boot.autoconfigure.condition.ConditionMessage.forCondition;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.noMatch;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.Style;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.github.trang.autoconfigure.redisson.enums.ClientMode;


/**
 * Redisson 的判断条件
 * <p>
 * Write the code. Change the world.
 *
 * @author trang
 * @date 2018/8/1
 */
class RedissonConditions {

    private static final String CLIENT_MODE_PROPERTY = "redisson.client-mode";

    /**
     * RedissonAutoConfiguration 的判断条件
     * <p>
     * redisson.client-mode != none
     */
    static class RedissonCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult<Set<ClientMode>> bindResult = Binder.get(context.getEnvironment())
                    .bind(CLIENT_MODE_PROPERTY, Bindable.setOf(ClientMode.class));
            ConditionMessage.Builder otherMessage = forCondition("RedissonCondition");
            if (!bindResult.isBound()) {
                return match(otherMessage.because(String.format("automatic client mode '%s'", ClientMode.DEFAULT)));
            }
            try {
                ConditionMessage message =
                        forCondition("RedissonCondition", String.format("(%s=%s)", CLIENT_MODE_PROPERTY, bindResult.get()))
                                .found("client mode").items(Style.QUOTE, bindResult.get());
                // client-mode=none 相当于禁用自动配置
                return !bindResult.get().contains(ClientMode.NONE) ? match(message) : noMatch(message);
            } catch (BindException e) {
                return noMatch(otherMessage.found("unknown value").items(Style.QUOTE, bindResult.get()));
            }
        }

    }

    /**
     * RedissonClient 的判断条件
     * <p>
     * redisson.client-mode == null || redisson.client-mode == default 时生效
     */
    static class RedissonClientCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult<Set<ClientMode>> bindResult = Binder.get(context.getEnvironment())
                    .bind(CLIENT_MODE_PROPERTY, Bindable.setOf(ClientMode.class));
            if (!bindResult.isBound()) {
                return match(forCondition("RedissonClientCondition")
                                     .because(String.format("automatic client mode '%s'", ClientMode.DEFAULT)));
            }
            ConditionMessage message =
                    forCondition("RedissonClientCondition", String.format("(%s=%s)", CLIENT_MODE_PROPERTY, bindResult.get()))
                            .found("client mode").items(Style.QUOTE, bindResult.get());
            return bindResult.get().contains(ClientMode.DEFAULT) ? match(message) : noMatch(message);
        }

    }

    /**
     * RedissonReactiveClient 的判断条件
     * <p>
     * redisson.client-mode == reactive
     */
    static class RedissonReactiveClientCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult<Set<ClientMode>> bindResult = Binder.get(context.getEnvironment())
                    .bind(CLIENT_MODE_PROPERTY, Bindable.setOf(ClientMode.class));
            if (!bindResult.isBound()) {
                return noMatch(forCondition("RedissonReactiveClientCondition")
                                       .because(String.format("automatic client mode '%s'", ClientMode.DEFAULT)));
            }
            ConditionMessage message =
                    forCondition("RedissonReactiveClientCondition", String.format("(%s=%s)", CLIENT_MODE_PROPERTY, bindResult.get()))
                            .found("client mode").items(Style.QUOTE, bindResult.get());
            return bindResult.get().contains(ClientMode.REACTIVE) ? match(message) : noMatch(message);
        }

    }

}