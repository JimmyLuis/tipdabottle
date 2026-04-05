package de.lbeck.tipdabottle.customer.dto.viewstrategy;

import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.common.viewstrategy.ValidatorPrivateViewStrategy;
import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.repository.CustomerRepository;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Order(2)
@Component
public class CustomerPrivateStrategy implements ViewStrategy<Customer> {

    private final CustomerMapper customerMapper;
    private final AccountRepository accountRepository;

    public CustomerPrivateStrategy(CustomerMapper customerMapper, AccountRepository accountRepository) {
        this.customerMapper = customerMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Authentication auth, Customer entity) {
        return ValidatorPrivateViewStrategy.validatePrivateViewStrategy(accountRepository, auth, entity);
    }

    @Override
    public Object map(Customer entity) {
        return customerMapper.toPrivateDTO(entity);
    }

    @Override
    public Class<Customer> getType() {
        return Customer.class;
    }
}
