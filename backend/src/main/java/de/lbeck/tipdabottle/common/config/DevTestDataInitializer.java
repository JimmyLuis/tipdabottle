package de.lbeck.tipdabottle.common.config;

import de.lbeck.tipdabottle.account.enums.Role;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.repository.AccountRepository;
import de.lbeck.tipdabottle.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DevTestDataInitializer implements CommandLineRunner {
    private final AccountRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public DevTestDataInitializer(AccountRepository userRepo,
                                  PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {

        createUserIfNotExists("user@example.com", "password", Role.ROLE_PRIVATE);
        createUserIfNotExists("admin@example.com", "admin123", Role.ROLE_ADMIN);
        createUserIfNotExists("shared@example.com", "shared123", Role.ROLE_SHARED);
    }

    private void createUserIfNotExists(String email, String rawPassword, Role role) {

        if (userRepo.findAccountByEmail(email).isEmpty()) {

            Account user = new Account();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);

            if (email.equals("user@example.com")) user.setCustomer(customerRepository.findById(1L).get());

            userRepo.save(user);

            System.out.println(" Created user: " + email);
        }
    }
}
