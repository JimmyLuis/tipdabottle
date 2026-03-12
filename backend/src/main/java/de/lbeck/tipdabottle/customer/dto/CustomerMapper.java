package de.lbeck.tipdabottle.customer.dto;

import de.lbeck.tipdabottle.customer.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
