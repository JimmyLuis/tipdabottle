package de.lbeck.tipdabottle.account.dto;

import de.lbeck.tipdabottle.account.dto.out.ResponseAccountPublicDTO;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface AccountMapper  {

    ResponseAccountPublicDTO toPublicDTO(Account account);

}
