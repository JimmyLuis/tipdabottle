package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PurchaseAsGroupPublicStrategy implements ViewStrategy<> {
    @Override
    public boolean supports(Authentication auth, Object entity) {
        return false; //todo hier weitermachen
    }

    @Override
    public Object map(Object entity) {
        return null;
    }

    @Override
    public Class getType() {
        return null;
    }
}
