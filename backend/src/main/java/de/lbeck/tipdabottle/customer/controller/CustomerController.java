package de.lbeck.tipdabottle.customer.controller;

import de.lbeck.tipdabottle.customer.dto.CustomerBalanceDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers(@RequestParam (defaultValue = "0") Boolean listInactiveProfiles) {
        return customerService.getAllCustomers(listInactiveProfiles);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping(params = "email")
    public CustomerDTO getCustomerByEMail(@RequestParam @NotEmpty String email){
        return customerService.getCustomerEMail(email);
    }

    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerCreateDTO customerCreateDTO){
        return customerService.createCustomer(customerCreateDTO);
    }

    @PostMapping("/{id}/balance")
    public CustomerDTO changeCustomerBalance(@PathVariable Long id, @Valid @RequestBody CustomerBalanceDTO customerBalanceDTO){
        return customerService.changeCustomerBalance(id, customerBalanceDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@Valid @RequestBody CustomerUpdateDTO customerUpdateDTO, @PathVariable Long id){
        return customerService.updateCustomer(id, customerUpdateDTO );
    }


}
