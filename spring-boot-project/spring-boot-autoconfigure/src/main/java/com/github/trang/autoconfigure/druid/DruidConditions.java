package com.github.trang.autoconfigure.druid;

import static org.springframework.boot.autoconfigure.condition.ConditionMessage.forCondition;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.noMatch;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * DruidConditions
 * <p>
 * Write the code. Change the world.
 *
 * @author trang
 * @date 2018/8/1
 */
public class DruidConditions {

    public static final String DYNAMIC_PROPERTIES = "spring.datasource.druid.data-sources";

    public static class SingleDataSourceCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult<Map<String, Object>> bindResult = Binder.get(context.getEnvironment())
                    .bind(DYNAMIC_PROPERTIES, Bindable.mapOf(String.class, Object.class));
            ConditionMessage message = forCondition("SingleDataSourceCondition")
                    .found(DYNAMIC_PROPERTIES + "=" + bindResult.isBound()).atAll();
            return bindResult.isBound() ? noMatch(message) : match(message);
        }

    }

    public static class DynamicDataSourceCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BindResult<Map<String, Object>> bindResult = Binder.get(context.getEnvironment())
                    .bind(DYNAMIC_PROPERTIES, Bindable.mapOf(String.class, Object.class));
            ConditionMessage message = forCondition("DynamicDataSourceCondition")
                    .found(DYNAMIC_PROPERTIES + "=" + bindResult.isBound()).atAll();
            return bindResult.isBound() ? match(message) : noMatch(message);
        }

    }

}