package de.lbeck.tipdabottle.customer.dto;

import de.lbeck.tipdabottle.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);

    Customer toEntity(CustomerCreateDTO customerCreateDTO);
    Customer toEntity(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer customer);

}
