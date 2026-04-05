package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.common.viewstrategy.ValidatorPrivateViewStrategy;
import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.purchase.dto.PurchaseGroupMapper;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class PurchaseGroupPrivateStrategy  implements ViewStrategy<PurchaseGroup> {

    private final PurchaseGroupMapper purchaseGroupMapper;
    private final AccountRepository accountRepository;

    public PurchaseGroupPrivateStrategy(PurchaseGroupMapper purchaseGroupMapper, AccountRepository accountRepository) {
        this.purchaseGroupMapper = purchaseGroupMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Authentication auth, PurchaseGroup entity) {
        return false; //todo momentan nich nicht möglich, da eigene bestellungen mit anderen gemischt sind --> noch keine weiteren infos preisgeben wie bei public auch
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
