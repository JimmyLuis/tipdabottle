package de.lbeck.tipdabottle.customer.dto.viewstrategy;

import de.lbeck.tipdabottle.common.viewstrategy.ViewStrategy;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class CustomerPublicStrategy implements ViewStrategy<Customer> {

    private final CustomerMapper customerMapper;

    public CustomerPublicStrategy(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }


    @Override
    public boolean supports(Authentication auth, Customer entity) {
        return true;
    }

    @Override
    public Object map(Customer entity) {
        return customerMapper.toPublicDTO(entity);
    }

    @Override
    public Class<Customer> getType() {
        return Customer.class;
    }
}
