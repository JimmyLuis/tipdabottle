package de.lbeck.tipdabottle.customer.controller;

import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.dto.response.ResponseCustomerDTO;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerBalanceDTO;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    public List<ResponseCustomerDTO> getAllCustomers(@RequestParam (defaultValue = "0") Boolean listInactiveProfiles) {
        return customerService.getAllCustomers(listInactiveProfiles);
    }

    @GetMapping("/{id}")
    public ResponseCustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping(params = "email")
    public ResponseCustomerDTO getCustomerByEMail(@RequestParam @NotEmpty String email){
        return customerService.getCustomerEMail(email);
    }

    @PostMapping
    public ResponseCustomerDTO createCustomer(@Valid @RequestBody RequestCustomerCreateDTO requestCustomerCreateDTO){
        return customerService.createCustomer(requestCustomerCreateDTO);
    }

    @PostMapping("/{id}/balance")
    public ResponseCustomerDTO changeCustomerBalance(@PathVariable Long id, @Valid @RequestBody RequestCustomerBalanceDTO requestCustomerBalanceDTO){
        return customerService.changeCustomerBalance(id, requestCustomerBalanceDTO);
    }

    @PutMapping("/{id}")
    public ResponseCustomerDTO updateCustomer(@Valid @RequestBody RequestCustomerUpdateDTO requestCustomerUpdateDTO, @PathVariable Long id){
        return customerService.updateCustomer(id, requestCustomerUpdateDTO);
    }


}
