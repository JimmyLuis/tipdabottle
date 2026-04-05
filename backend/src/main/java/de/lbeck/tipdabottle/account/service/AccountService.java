package de.lbeck.tipdabottle.account.service;

import de.lbeck.tipdabottle.account.dto.in.RequestAccountCreateDTO;
import de.lbeck.tipdabottle.account.dto.in.RequestAccountLoginDTO;
import de.lbeck.tipdabottle.account.dto.out.ResponseAccountPublicDTO;
import de.lbeck.tipdabottle.account.enums.Role;
import de.lbeck.tipdabottle.account.exception.AccountNotFoundException;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.customer.exception.CustomerNotFoundException;
import de.lbeck.tipdabottle.customer.exception.EmailAlreadyExistsException;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.repository.CustomerRepository;
import de.lbeck.tipdabottle.purchase.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PurchaseRepository purchaseRepository;

    public AccountService(AuthenticationManager authenticationManager, JwtService jwtService, AccountRepository accountRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, PurchaseRepository purchaseRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.purchaseRepository = purchaseRepository;
    }

    public ResponseAccountPublicDTO login(RequestAccountLoginDTO requestAccountLoginDTO) {
        Account account = accountRepository.getAccountByEmail(requestAccountLoginDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("No account found for provided Email!"));

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getId(),
                        requestAccountLoginDTO.password()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Account customerAccount = accountRepository.findAccountById(Long.parseLong(userDetails.getUsername()))
                .orElseThrow();

        String token = jwtService.generateToken(userDetails, customerAccount.getId()+"");

        return new ResponseAccountPublicDTO(customerAccount.getEmail(), customerAccount.getRole(), customerAccount.getCustomer(), token);
    }

    public ResponseAccountPublicDTO logout(){
        return null; //todo muss noch nicht implementiert werden
    }

    public Account createAccount(RequestAccountCreateDTO requestAccountCreateDTO){
        accountRepository.findAccountByEmail(requestAccountCreateDTO.email()).ifPresent(account -> {
            throw new EmailAlreadyExistsException("Account with this email already exists!");
        });
        if (requestAccountCreateDTO.role().equals(Role.ROLE_PRIVATE)){
            return accountRepository.save(createAccountWithCustomer(requestAccountCreateDTO));
        }
        return new Account(null,
                requestAccountCreateDTO.email(),
                passwordEncoder.encode(requestAccountCreateDTO.password()),
                Role.ROLE_PRIVATE,
                null);
    }

    private Account createAccountWithCustomer(RequestAccountCreateDTO requestAccountCreateDTO){
        Customer customer = new Customer(null,
                requestAccountCreateDTO.firstName(),
                requestAccountCreateDTO.lastName(),
                requestAccountCreateDTO.email(),
                0.0,
                false,
                false,
                true);
        customer = customerRepository.save(customer);

        return new Account(null,
                customer.getEmail(),
                passwordEncoder.encode(requestAccountCreateDTO.password()),
                Role.ROLE_PRIVATE,
                customer);
    }

    @Transactional
    public Account deleteAccountByEmail(String email){
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account with email " + email + " not found!"));
        if (account.getCustomer() != null){
            purchaseRepository.detachCustomer(account.getCustomer().getId());
            customerRepository.delete(account.getCustomer());
        }
        accountRepository.delete(account);
        return account;
    }

}
