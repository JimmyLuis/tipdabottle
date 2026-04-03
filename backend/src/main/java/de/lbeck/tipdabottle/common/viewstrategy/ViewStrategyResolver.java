package de.lbeck.tipdabottle.common.viewstrategy;

import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewStrategyResolver {
    private final List<ViewStrategy<?>> strategies;

    public ViewStrategyResolver(List<ViewStrategy<?>> strategies) {
        this.strategies = strategies;
    }

    @SuppressWarnings("unchecked")
    public <T> ViewStrategy<T> resolve(Authentication auth, T entity) {

        Class<?> entityClass = Hibernate.getClass(entity);

        return (ViewStrategy<T>) strategies.stream()
                .filter(s -> s.getType().isAssignableFrom(entityClass))
                .filter(s -> ((ViewStrategy<T>) s).supports(auth, entity))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No strategy found"));
    }
}
