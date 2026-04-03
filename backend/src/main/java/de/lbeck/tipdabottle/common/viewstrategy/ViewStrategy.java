package de.lbeck.tipdabottle.common.viewstrategy;

import org.springframework.security.core.Authentication;

public interface ViewStrategy<T> {

    boolean supports(Authentication auth, T entity);

    Object map(T entity);

    Class<T> getType();
}
