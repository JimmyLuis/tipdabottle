package de.lbeck.tipdabottle.common.viewstrategy;

import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.customer.model.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.atomic.AtomicBoolean;

public class ValidatorPrivateViewStrategy {

    public static boolean validatePrivateViewStrategy(AccountRepository accountRepository, Authentication auth, Customer entity){
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRIVATE"))) return false;
        AtomicBoolean isCorrectCustomer = new AtomicBoolean(false);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        accountRepository.findAccountById(Long.parseLong(userDetails.getUsername()))
                .ifPresent((account) -> {
                    if (account.getCustomer() != null){
                        if (account.getCustomer().getId().equals(entity.getId())) isCorrectCustomer.set(true);
                    }
                });
        return isCorrectCustomer.get();
    }
}
