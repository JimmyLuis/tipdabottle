package de.lbeck.tipdabottle.customer.service;

import de.lbeck.tipdabottle.customer.dto.CustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.dto.CustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.exception.CustomerNotFoundException;
import de.lbeck.tipdabottle.customer.exception.EmailAlreadyExistsException;
import de.lbeck.tipdabottle.customer.model.Customer;
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

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO getCustomerEMail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + email + " not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerMapper.toEntity(customerCreateDTO);
        customerRepository.findByEmail(customerCreateDTO.email())
                .ifPresent(mailCustomer -> {
                    throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                });
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        Customer finalCustomer = customer;
        customerRepository.findByEmail(customerUpdateDTO.email())
                .ifPresent(mailCustomer -> {
                    if (mailCustomer.getEmail().equals(finalCustomer.getEmail())) {
                        throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                    }
                });
        customer = customerMapper.toEntity(customerUpdateDTO, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(Customer customer){
        return customerMapper.toDTO(customerRepository.save(customer));
    }

}
