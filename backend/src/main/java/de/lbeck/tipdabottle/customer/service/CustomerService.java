package de.lbeck.tipdabottle.customer.service;

import de.lbeck.tipdabottle.customer.dto.*;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerBalanceDTO;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerCreateDTO;
import de.lbeck.tipdabottle.customer.dto.request.RequestCustomerUpdateDTO;
import de.lbeck.tipdabottle.customer.dto.response.ResponseCustomerDTO;
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

    public List<ResponseCustomerDTO> getAllCustomers(Boolean listInactiveProfiles) {
        List<ResponseCustomerDTO> responseCustomerDTOS = new ArrayList<>();
        if (listInactiveProfiles) {
            customerRepository.findAll().forEach(customer -> {
                responseCustomerDTOS.add(customerMapper.toDTO(customer));
            });
        } else {
            customerRepository.findAllByActiveProfileIsTrue().forEach(customer -> {
                responseCustomerDTOS.add(customerMapper.toDTO(customer));
            });
        }
        return responseCustomerDTOS;
    }

    public ResponseCustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
        return customerMapper.toDTO(customer);
    }

    public ResponseCustomerDTO getCustomerEMail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + email + " not found"));
        return customerMapper.toDTO(customer);
    }

    public ResponseCustomerDTO createCustomer(RequestCustomerCreateDTO requestCustomerCreateDTO) {
        Customer customer = customerMapper.toEntity(requestCustomerCreateDTO);
        customerRepository.findByEmail(requestCustomerCreateDTO.email())
                .ifPresent(mailCustomer -> {
                    throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                });
        customer.setLocked(false);
        customer.setActiveProfile(true);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public ResponseCustomerDTO updateCustomer(Long id, RequestCustomerUpdateDTO requestCustomerUpdateDTO) {
        Customer customer = customerMapper.toEntity(getCustomerById(id));
        Customer finalCustomer = customer;
        customerRepository.findByEmail(requestCustomerUpdateDTO.email())
                .ifPresent(mailCustomer -> {
                    if (mailCustomer.getEmail().equals(requestCustomerUpdateDTO.email()) && !Objects.equals(mailCustomer.getId(), id)) {
                        throw new EmailAlreadyExistsException("Customer with email " + mailCustomer.getEmail() + " already exists!");
                    }
                });
        customer = customerMapper.toEntity(requestCustomerUpdateDTO, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public ResponseCustomerDTO updateCustomer(Customer customer){
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public ResponseCustomerDTO changeCustomerBalance(Long id, RequestCustomerBalanceDTO requestCustomerBalanceDTO) {
        Customer customer = customerMapper.toEntity(getCustomerById(id));
        customer.setBalance(customer.getBalance() + requestCustomerBalanceDTO.addedBalance());
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

}
