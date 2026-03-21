package de.lbeck.tipdabottle.purchase.repository;

import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseGroupRepository extends CrudRepository<PurchaseGroup, Long> {
    @Query("SELECT DISTINCT purchaseGroup FROM PurchaseGroup purchaseGroup ORDER BY purchaseGroup.id DESC")
    Page<PurchaseGroup> findGroupIdsOrdered(Pageable pageable);
}
