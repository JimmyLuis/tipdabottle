package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.purchase.dto.PurchaseGroupMapper;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class PurchaseGroupAdminStrategy  implements ViewStrategy<PurchaseGroup> {

    private final PurchaseGroupMapper purchaseGroupMapper;

    public PurchaseGroupAdminStrategy(PurchaseGroupMapper purchaseGroupMapper) {
        this.purchaseGroupMapper = purchaseGroupMapper;
    }

    @Override
    public boolean supports(Authentication auth, PurchaseGroup entity) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Object map(PurchaseGroup entity) {
        return purchaseGroupMapper.toAdminDTO(entity);
    }

    @Override
    public Class<PurchaseGroup> getType() {
        return PurchaseGroup.class;
    }
}
