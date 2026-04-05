package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.purchase.dto.PurchaseMapper;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class PurchaseAdminStrategy implements ViewStrategy<Purchase> {

    private final PurchaseMapper purchaseMapper;

    public PurchaseAdminStrategy(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public boolean supports(Authentication auth, Purchase entity) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Object map(Purchase entity) {
        return purchaseMapper.toAdminDTO(entity);
    }

    @Override
    public Class<Purchase> getType() {
        return Purchase.class;
    }
}
