
package com.example.supplychain.repository;

import com.example.supplychain.entity.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SupplyOrderRepository
        extends JpaRepository<SupplyOrder, Long> {

    @Query("""
        SELECT o.productName, SUM(o.quantity)
        FROM SupplyOrder o
        WHERE o.orderDate >= :startDate
          AND o.orderDate <= :endDate
          AND (o.status IS NULL OR LOWER(o.status) <> 'cancelled')
        GROUP BY o.productName
        """)
    List<Object[]> findProductDemand(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
