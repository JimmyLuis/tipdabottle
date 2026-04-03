package de.lbeck.tipdabottle.customer.service;

import de.lbeck.tipdabottle.customer.dto.*;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerBalanceDTO;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.in.RequestCustomerUpdateDTO;
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

    public List<Customer> getAllCustomers(Boolean listInactiveProfiles) {
        List<Customer> responseCustomers = new ArrayList<>();
        if (listInactiveProfiles) {
            customerRepository.findAll().forEach(responseCustomers::add);
        } else {
            customerRepository.findAllByActiveProfileIsTrue().forEach(responseCustomers::add);
        }
        return responseCustomers;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    public Customer getCustomerEMail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + email + " not found"));
    }

    public Customer createCustomer(RequestCustomerCreateDTO requestCustomerCreateDTO) {
        Customer customer = customerMapper.toEntity(requestCustomerCreateDTO);
        customerRepository.findByEmail(requestCustomerCreateDTO.email())
                .ifPresent(mailCustomer -> {
                    throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                });
        customer.setLocked(false);
        customer.setActiveProfile(true);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, RequestCustomerUpdateDTO requestCustomerUpdateDTO) {
        Customer customer = getCustomerById(id);
        customerRepository.findByEmail(requestCustomerUpdateDTO.email())
                .ifPresent(mailCustomer -> {
                    if (mailCustomer.getEmail().equals(requestCustomerUpdateDTO.email()) && !Objects.equals(mailCustomer.getId(), id)) {
                        throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                    }
                });
        customer = customerMapper.toEntity(requestCustomerUpdateDTO, customer);
        return  customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer changeCustomerBalance(Long id, RequestCustomerBalanceDTO requestCustomerBalanceDTO) {
        Customer customer = getCustomerById(id);
        customer.setBalance(customer.getBalance() + requestCustomerBalanceDTO.addedBalance());
        return customerRepository.save(customer);
    }

}
