package diakonidze.kartlos.voiage.models;

/**
 * Created by k.diakonidze on 8/25/2015.
 */
public class CityObj {
    public String name, prevCity;
    public int distance, time;

    public CityObj(String name, String prevCity, int distance, int time) {
        this.name = name;
        this.prevCity = prevCity;
        this.distance = distance;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrevCity() {
        return prevCity;
    }

    public void setPrevCity(String prevCity) {
        this.prevCity = prevCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
