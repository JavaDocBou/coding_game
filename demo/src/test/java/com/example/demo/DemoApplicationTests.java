package com.example.demo;

import controller.JourneysCalculationService;
import model.Customer;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    JourneysCalculationService calculationService;

    @Test
    void when_sending_list_with_four_customers_then_result_should_include_all_journeys_details_per_customer() throws IOException, JSONException {
        String jsonRequest = IOUtils.toString(Objects.requireNonNull(this.getClass().getResource("/flux/list_journeys.json")), StandardCharsets.UTF_8);
        List<Customer> customers = calculationService.setListCustomers(jsonRequest);
        Assertions.assertTrue(customers.size() == 4);
        Assertions.assertTrue(customers.get(0).getTotalCostInCents() == 240);
        Assertions.assertTrue(customers.get(1).getTotalCostInCents() == 720);
        Assertions.assertTrue(customers.get(2).getTotalCostInCents() == 440);
        Assertions.assertTrue(customers.get(3).getTotalCostInCents() == 500);
        Assertions.assertTrue(customers.get(0).getTrips().size() == 1);
        Assertions.assertTrue(customers.get(1).getTrips().size() == 3);
        Assertions.assertTrue(customers.get(2).getTrips().size() == 2);
        Assertions.assertTrue(customers.get(3).getTrips().size() == 2);
    }

}
