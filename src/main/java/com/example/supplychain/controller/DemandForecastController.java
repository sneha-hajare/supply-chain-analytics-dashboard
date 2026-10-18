
package com.example.supplychain.controller;

import com.example.supplychain.service.DemandForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemandForecastController {

    private final DemandForecastService forecastService;

    public DemandForecastController(
            DemandForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/forecast")
    public String showForecast(Model model) {

        model.addAttribute(
                "forecasts",
                forecastService.getDemandForecasts()
        );

        return "forecast";
    }
}
