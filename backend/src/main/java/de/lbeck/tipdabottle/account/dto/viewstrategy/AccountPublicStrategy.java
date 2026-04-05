package de.lbeck.tipdabottle.account.dto.viewstrategy;

import de.lbeck.tipdabottle.account.dto.AccountMapper;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccountPublicStrategy implements ViewStrategy<Account> {

    private final AccountMapper accountMapper;

    public AccountPublicStrategy(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public boolean supports(Authentication auth, Account entity) {
        return true;
    }

    @Override
    public Object map(Account entity) {
        return accountMapper.toPublicDTO(entity);
    }

    @Override
    public Class<Account> getType() {
        return Account.class;
    }
}
