package de.lbeck.tipdabottle.purchase.dto.viewstrategy;

import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.common.viewstrategy.ValidatorPrivateViewStrategy;
import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.purchase.dto.PurchaseMapper;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class PurchasePrivateStrategy implements ViewStrategy<Purchase> {
    private final PurchaseMapper purchaseMapper;
    private final AccountRepository accountRepository;

    public PurchasePrivateStrategy(PurchaseMapper purchaseMapper, AccountRepository accountRepository) {
        this.purchaseMapper = purchaseMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Authentication auth, Purchase entity) {
        Customer customer = entity.getCustomer();
        if (customer == null) return false;
        return ValidatorPrivateViewStrategy.validatePrivateViewStrategy(accountRepository, auth, customer);
    }

    @Override
    public Object map(Purchase entity) {
        return purchaseMapper.toPrivateDTO(entity);
    }

    @Override
    public Class<Purchase> getType() {
        return Purchase.class;
    }
}
