package de.lbeck.tipdabottle.account.service;

import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {


    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("Account does not exist!"));

        return User.withUsername(account.getId()+"")
                .password(account.getPassword())
                .roles(account.getRole().name().replace("ROLE_", ""))
                .build();
    }
}
