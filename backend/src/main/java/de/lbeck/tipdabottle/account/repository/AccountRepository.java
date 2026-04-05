package de.lbeck.tipdabottle.account.repository;

import de.lbeck.tipdabottle.account.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);

    Optional<Account> getAccountByEmail(String email);

    Optional<Account> findAccountById(Long id);
}
