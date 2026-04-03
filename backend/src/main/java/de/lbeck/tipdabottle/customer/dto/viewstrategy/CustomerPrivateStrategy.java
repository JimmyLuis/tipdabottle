package de.lbeck.tipdabottle.customer.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class CustomerPrivateStrategy implements ViewStrategy<Customer> {

    private final CustomerMapper customerMapper;

    public CustomerPrivateStrategy(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public boolean supports(Authentication auth, Customer entity) {
        return true; //todo : rollen etc
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
