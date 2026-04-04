package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.purchase.dto.PurchaseGroupMapper;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class PurchaseGroupPrivateStrategy  implements ViewStrategy<PurchaseGroup> {

    private final PurchaseGroupMapper purchaseGroupMapper;

    public PurchaseGroupPrivateStrategy(PurchaseGroupMapper purchaseGroupMapper) {
        this.purchaseGroupMapper = purchaseGroupMapper;
    }

    @Override
    public boolean supports(Authentication auth, PurchaseGroup entity) {
        return false;
    }

    @Override
    public Object map(PurchaseGroup entity) {
        return purchaseGroupMapper.toPrivateDTO(entity);
    }

    @Override
    public Class<PurchaseGroup> getType() {
        return PurchaseGroup.class;
    }
}
