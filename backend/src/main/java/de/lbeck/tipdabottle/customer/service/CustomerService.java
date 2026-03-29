package de.lbeck.tipdabottle.customer.service;

import de.lbeck.tipdabottle.customer.dto.*;
import de.lbeck.tipdabottle.customer.exception.CustomerNotFoundException;
import de.lbeck.tipdabottle.customer.exception.EmailAlreadyExistsException;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomers(Boolean listInactiveProfiles) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        if (listInactiveProfiles) {
            customerRepository.findAll().forEach(customer -> {
                customerDTOs.add(customerMapper.toDTO(customer));
            });
        } else {
            customerRepository.findAllByActiveProfileIsTrue().forEach(customer -> {
                customerDTOs.add(customerMapper.toDTO(customer));
            });
        }
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
        customer.setLocked(false);
        customer.setActiveProfile(true);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO) {
        Customer customer = customerMapper.toEntity(getCustomerById(id));
        Customer finalCustomer = customer;
        customerRepository.findByEmail(customerUpdateDTO.email())
                .ifPresent(mailCustomer -> {
                    if (mailCustomer.getEmail().equals(customerUpdateDTO.email()) && !Objects.equals(mailCustomer.getId(), id)) {
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

    public CustomerDTO changeCustomerBalance(Long id, CustomerBalanceDTO customerBalanceDTO) {
        Customer customer = customerMapper.toEntity(getCustomerById(id));
        customer.setBalance(customer.getBalance() + customerBalanceDTO.addedBalance());
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

}
