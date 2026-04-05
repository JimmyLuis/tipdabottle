package de.lbeck.tipdabottle.customer.controller;

import de.lbeck.tipdabottle.common.annotations.View;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerBalanceDTO;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @View(Customer.class)
    @GetMapping
    public List<Customer> getAllCustomers(@RequestParam (defaultValue = "0") Boolean listInactiveProfiles) {
        return customerService.getAllCustomers(listInactiveProfiles);
    }

    @View(Customer.class)
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @View(Customer.class)
    @GetMapping(params = "email")
    public Customer getCustomerByEMail(@RequestParam @NotEmpty String email){
        return customerService.getCustomerEMail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @View(Customer.class)
    @PostMapping
    public Customer createCustomer(@Valid @RequestBody RequestCustomerCreateDTO requestCustomerCreateDTO){
        return customerService.createCustomer(requestCustomerCreateDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @View(Customer.class)
    @PostMapping("/{id}/balance")
    public Customer changeCustomerBalance(@PathVariable Long id, @Valid @RequestBody RequestCustomerBalanceDTO requestCustomerBalanceDTO){
        return customerService.changeCustomerBalance(id, requestCustomerBalanceDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @View(Customer.class)
    @PutMapping("/{id}")
    public Customer updateCustomer(@Valid @RequestBody RequestCustomerUpdateDTO requestCustomerUpdateDTO, @PathVariable Long id){
        return customerService.updateCustomer(id, requestCustomerUpdateDTO);
    }


}
