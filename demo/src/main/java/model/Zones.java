package model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class Zones {

    private HashMap<Integer, List<String>> listZones;
    private List<String> stationsZone3;
    private List<String> stationsZone4;

    public Zones() {
        this.listZones = new HashMap<>();
        this.listZones.put(1, Arrays.asList("A", "B"));
        this.listZones.put(2, Arrays.asList("C", "D", "E"));
        this.listZones.put(3, Arrays.asList("C", "E", "F"));
        this.listZones.put(4, Arrays.asList("F", "G", "H", "I"));
        this.stationsZone3 = Arrays.asList("C", "E", "F");
        this.stationsZone4 = Arrays.asList("F", "G", "H", "I");

    }
    public HashMap<Integer, List<String>> getListZones() {
        return listZones;
    }

    public List<String> getStationsZone3() {
        return stationsZone3;
    }

    public List<String> getStationsZone4() {
        return stationsZone4;
    }
}
