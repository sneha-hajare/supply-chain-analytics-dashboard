
package com.example.supplychain.service;

import com.example.supplychain.dto.DemandForecast;
import com.example.supplychain.entity.Inventory;
import com.example.supplychain.repository.SupplyOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandForecastService {

    private final SupplyOrderRepository supplyOrderRepository;
    private final InventoryService inventoryService;

    public DemandForecastService(
            SupplyOrderRepository supplyOrderRepository,
            InventoryService inventoryService) {
        this.supplyOrderRepository = supplyOrderRepository;
        this.inventoryService = inventoryService;
    }

    public List<DemandForecast> getDemandForecasts() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29);

        Map<String, Long> demandMap = new LinkedHashMap<>();

        // Include products from inventory, even without recent orders.
        List<Inventory> inventoryList =
                inventoryService.getAllInventory();

        for (Inventory item : inventoryList) {
            if (item.getProductName() != null
                    && !item.getProductName().isBlank()) {
                demandMap.putIfAbsent(item.getProductName(), 0L);
            }
        }

        // Add actual demand from the last 30 days.
        List<Object[]> demandData =
                supplyOrderRepository.findProductDemand(
                        startDate, endDate);

        for (Object[] row : demandData) {
            String productName = (String) row[0];

            long quantity = row[1] == null
                    ? 0L
                    : ((Number) row[1]).longValue();

            if (productName != null && !productName.isBlank()) {
                demandMap.merge(productName, quantity, Long::sum);
            }
        }

        List<DemandForecast> forecasts = new ArrayList<>();

        for (Map.Entry<String, Long> entry : demandMap.entrySet()) {
            long totalQuantity = entry.getValue();

            double averageDailyDemand = totalQuantity / 30.0;

            long forecastNext30Days =
                    Math.round(averageDailyDemand * 30);

            forecasts.add(new DemandForecast(
                    entry.getKey(),
                    totalQuantity,
                    averageDailyDemand,
                    forecastNext30Days
            ));
        }

        return forecasts;
    }
}
