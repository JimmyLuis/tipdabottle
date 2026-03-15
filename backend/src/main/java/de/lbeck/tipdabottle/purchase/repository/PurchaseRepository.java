package de.lbeck.tipdabottle.purchase.repository;

import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByPurchaseGroup_Id(Long purchaseGroupId);
    Page<Purchase> findAll(Pageable pageable);

    Page<Purchase> findAllByCustomer_Id(Long customerId, Pageable pageable);
}
