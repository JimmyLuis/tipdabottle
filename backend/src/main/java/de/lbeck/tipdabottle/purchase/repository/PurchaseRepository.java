package de.lbeck.tipdabottle.purchase.repository;

import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.springframework.data.repository.CrudRepository;

interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}
