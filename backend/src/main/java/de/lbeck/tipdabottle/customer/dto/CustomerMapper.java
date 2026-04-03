package de.lbeck.tipdabottle.customer.dto;

import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.dto.out.ResponseCustomerAdminDTO;
import de.lbeck.tipdabottle.customer.dto.out.ResponseCustomerPrivateDTO;
import de.lbeck.tipdabottle.customer.dto.out.ResponseCustomerPublicDTO;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(RequestCustomerCreateDTO requestCustomerCreateDTO);
    @Mapping(target = "id", ignore = true)
    Customer toEntity(RequestCustomerUpdateDTO requestCustomerUpdateDTO, @MappingTarget Customer customer);


    ResponseCustomerPublicDTO toPublicDTO(Customer customer);
    ResponseCustomerPrivateDTO toPrivateDTO(Customer customer);
    ResponseCustomerAdminDTO toAdminDTO(Customer customer);

}
