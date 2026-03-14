package de.lbeck.tipdabottle.customer.controller;

import de.lbeck.tipdabottle.customer.dto.CustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping(params = "email")
    public CustomerDTO getCustomerByEMail(@RequestParam String email){
        return customerService.getCustomerEMail(email);
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerCreateDTO customerCreateDTO){
        return customerService.createCustomer(customerCreateDTO);
    }

    @PutMapping("{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerUpdateDTO customerUpdateDTO, @PathVariable Long id){
        return customerService.updateCustomer(id, customerUpdateDTO );
    }


}
