package de.lbeck.tipdabottle.customer.repository;

import de.lbeck.tipdabottle.customer.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Iterable<Customer> findAllByActiveProfileIsTrue();

}
