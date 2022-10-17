package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Customer {

    @JsonProperty("customerId")
    private int customerId;
    @JsonProperty("totalCostInCents")
    private int totalCostInCents;
    @JsonProperty(value = "trips")
    private List<Trip> trips;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(int totalCostInCents) {
        this.totalCostInCents = totalCostInCents;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "{" +
                "customerId :" + customerId +
                ", totalCostInCents :" + totalCostInCents +
                ", trips=" + trips +
                '}';
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
