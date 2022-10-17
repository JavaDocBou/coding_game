package controller;

import model.Customer;
import model.Trip;
import model.Zones;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class JourneysCalculationService {
    Zones listZones;
    int stationDeparture, stationArrival = 0;
    int totalCostCustomer;

    public JourneysCalculationService() {
        this.listZones = new Zones();
    }

    public List<Customer> setListCustomers(String request) throws JSONException {
        List<Customer> customers = new ArrayList<>();
        JSONObject object = new JSONObject(request);
        JSONArray taps = object.getJSONArray("taps");
        List<JSONObject> list = new ArrayList<>();
        for (int index = 0; index < taps.length(); index++) {
            list.add(taps.getJSONObject(index));
        }

        Collections.sort(list, (o1, o2) -> {
            try {
                return Integer.compare(o1.getInt("customerId"), o2.getInt("customerId"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        // Given all trips come two by two per customer
        // we iterate the list while checking if the next even index has the same customerId
        // if not, then we move to the next customer to get all his/her trips
        for (int index = 0; index < list.size(); index += 2) {
            Customer customer = new Customer();
            List<Trip> listTrips = new ArrayList<>();
            totalCostCustomer = 0;
            int customerId = Integer.parseInt(list.get(index).getString("customerId"));
            customer.setCustomerId(customerId);
            setTripDetails(index, list, listTrips);
            boolean isSameCustomer;

            do {
                if (index + 2 < list.size() && Integer.parseInt(list.get(index + 2).getString("customerId")) == customerId) {
                    isSameCustomer = true;
                    index += 2;
                    setTripDetails(index, list, listTrips);
                } else {
                    isSameCustomer = false;
                }
            } while (isSameCustomer);

            customer.setTotalCostInCents(totalCostCustomer);
            customer.setTrips(listTrips);
            customers.add(customer);
        }
        return customers;
    }

    private void setTripDetails(int index, List<JSONObject> list, List<Trip> listTrips) throws JSONException {
        String stationStart = list.get(index).getString("station");
        String stationEnd = list.get(index + 1).getString("station");
        int journeyStartTime = Integer.parseInt(list.get(index).getString("unixTimestamp"));
        Trip trip = formatTripDetails(journeyStartTime, stationStart, stationEnd);
        totalCostCustomer += trip.getCostInCents();
        listTrips.add(trip);
    }

    private Trip formatTripDetails(int journeyStart, String stationStart, String stationEnd) {
        Trip trip = new Trip();
        trip.setStartedJourneyAt(journeyStart);
        trip.setStationStart(stationStart);
        trip.setStationEnd(stationEnd);
        trip.setCostInCents(calculateJourneyPrice(stationStart, stationEnd));
        trip.setZoneFrom(stationDeparture);
        trip.setZoneTo(stationArrival);
        return trip;
    }

    // The whole logic of journeys price calculation is listed below
    // If the logic was to be modified/updated, we only need to change this block of code
    private int calculateJourneyPrice(String stationStart, String stationEnd) {
        int totalTrajet = 0;
        List<String> stationsZone3 = listZones.getStationsZone3();
        List<String> stationsZone4 = listZones.getStationsZone4();
        if (stationsZone3.contains(stationStart) && stationsZone3.contains(stationEnd)) {
            stationDeparture = 3;
            stationArrival = 3;
            totalTrajet += 2.00 * 100;
        } else if (stationsZone4.contains(stationStart) && stationsZone4.contains(stationEnd)) {
            stationDeparture = 4;
            stationArrival = 4;
            totalTrajet += 2.00 * 100;
        } else if (stationsZone3.contains(stationStart) && stationsZone4.contains(stationEnd)) {
            stationDeparture = 3;
            stationArrival = 4;
            totalTrajet += 2.00 * 100;
        } else if (stationsZone4.contains(stationStart) && stationsZone3.contains(stationEnd)) {
            stationDeparture = 4;
            stationArrival = 3;
            totalTrajet += 2.00 * 100;
        } else {
            stationDeparture = getStationNumber(stationStart);
            stationArrival = getStationNumber(stationEnd);
            if (stationDeparture == 1 && stationArrival == 2 || stationDeparture == 2 && stationArrival == 1) {
                totalTrajet += 2.40 * 100;
            } else if (stationDeparture == 3 && stationArrival == 4 || stationDeparture == 4 && stationArrival == 3) {
                totalTrajet += 2.00 * 100;
            } else if ((stationDeparture == 3 && (stationArrival == 1 || stationArrival == 2)) || (stationDeparture == 1 || stationDeparture == 2) && stationArrival == 3) {
                totalTrajet += 2.80 * 100;
            } else if ((stationDeparture == 4 && (stationArrival == 1 || stationArrival == 2)) || (stationDeparture == 1 || stationDeparture == 2) && stationArrival == 4) {
                totalTrajet += 3.00 * 100;
            }
        }
        return totalTrajet;
    }

    private Integer getStationNumber(String stationDepart) {
        List<Integer> stations = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : listZones.getListZones().entrySet()) {
            for (String station : entry.getValue()) {
                if (station.equalsIgnoreCase(stationDepart)) {
                    stations.add(entry.getKey());
                }
            }
        }
        return stations.get(0);
    }
}
