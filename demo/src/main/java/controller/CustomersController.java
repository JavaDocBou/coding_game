package controller;

import com.google.gson.Gson;
import model.Customer;
import model.Summaries;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CustomersController {

    @PostMapping("/customersSummaries")
    public ResponseEntity getCustomersSummaries(@RequestBody String request) throws JSONException {
        Summaries summaries = new Summaries();
        JourneysCalculationService journeysCalculationService = new JourneysCalculationService();
        List<Customer> customers = journeysCalculationService.setListCustomers(request);
        summaries.setCustomersSummaries(customers);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new Gson().toJson((summaries)));
    }

}
