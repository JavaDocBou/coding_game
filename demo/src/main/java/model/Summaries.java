package model;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Summaries {

    private List<Customer> customersSummaries;

    public List<Customer> getCustomersSummaries() {
        return customersSummaries;
    }

    public void setCustomersSummaries(List<Customer> customersSummaries) {
        this.customersSummaries = customersSummaries;
    }

    @Override
    public String toString() {
        return "{" +
                "customerSummaries : " + customersSummaries +
                '}';
    }
}
