package de.lbeck.tipdabottle.customer.dto;

import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.dto.response.ResponseCustomerDTO;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    ResponseCustomerDTO toDTO(Customer customer);
    Customer toEntity(ResponseCustomerDTO responseCustomerDTO);

    Customer toEntity(RequestCustomerCreateDTO requestCustomerCreateDTO);
    @Mapping(target = "id", ignore = true)
    Customer toEntity(RequestCustomerUpdateDTO requestCustomerUpdateDTO, @MappingTarget Customer customer);

}
