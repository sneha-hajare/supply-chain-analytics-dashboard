
package com.example.supplychain.dto;

public class DemandForecast {

    private String productName;
    private long totalQuantity;
    private double averageDailyDemand;
    private long forecastNext30Days;

    public DemandForecast(
            String productName,
            long totalQuantity,
            double averageDailyDemand,
            long forecastNext30Days) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.averageDailyDemand = averageDailyDemand;
        this.forecastNext30Days = forecastNext30Days;
    }

    public String getProductName() {
        return productName;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public double getAverageDailyDemand() {
        return averageDailyDemand;
    }

    public long getForecastNext30Days() {
        return forecastNext30Days;
    }
}
