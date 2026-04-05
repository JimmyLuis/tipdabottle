package de.lbeck.tipdabottle.customer.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Order(1)
@Component
public class CustomerAdminStrategy implements ViewStrategy<Customer> {

    private final CustomerMapper customerMapper;

    public CustomerAdminStrategy(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public boolean supports(Authentication auth, Customer entity) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Object map(Customer entity) {
        return customerMapper.toAdminDTO(entity);
    }

    @Override
    public Class<Customer> getType() {
        return Customer.class;
    }
}
