package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.purchase.dto.PurchaseMapper;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class PurchasePublicStrategy implements ViewStrategy<Purchase> {
    private final PurchaseMapper purchaseMapper;

    public PurchasePublicStrategy(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public boolean supports(Authentication auth, Purchase entity) {
        return true;
    }

    @Override
    public Object map(Purchase entity) {
        return purchaseMapper.toPublicDTO(entity);
    }

    @Override
    public Class<Purchase> getType() {
        return Purchase.class;
    }
}
