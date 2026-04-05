package de.lbeck.tipdabottle.purchase.repository;

import de.lbeck.tipdabottle.purchase.model.Purchase;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByPurchaseGroup_Id(Long purchaseGroupId);
    Page<Purchase> findAll(Pageable pageable);

    Page<Purchase> findAllByCustomer_Id(Long customerId, Pageable pageable);

    @Query("SELECT DISTINCT purchase.purchaseGroup FROM Purchase purchase ORDER BY purchase.purchaseGroup.id DESC")
    Page<PurchaseGroup> findGroupIds(Pageable pageable);


    List<Purchase> findAllByPurchaseGroupIn(List<PurchaseGroup> content);

    @Modifying
    @Query("UPDATE Purchase p SET p.customer = NULL WHERE p.customer.id = :id")
    void detachCustomer(@Param("id") Long id);
}
