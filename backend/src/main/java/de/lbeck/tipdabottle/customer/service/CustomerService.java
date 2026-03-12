package de.lbeck.tipdabottle.customer.service;

import de.lbeck.tipdabottle.container.repository.ContainerRepository;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            customerDTOs.add(customerMapper.toDTO(customer));
        });
        return customerDTOs;
    }
}
