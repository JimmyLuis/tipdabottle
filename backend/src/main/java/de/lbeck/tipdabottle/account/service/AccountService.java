package de.lbeck.tipdabottle.account.service;

import de.lbeck.tipdabottle.account.dto.in.RequestAccountLoginDTO;
import de.lbeck.tipdabottle.account.dto.out.ResponseAccountPublicDTO;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.repository.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    public AccountService(AuthenticationManager authenticationManager, JwtService jwtService, AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
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


        return null;
    }

}
