package models;

public class City {
    public String Name;
    public long Citizens;

    public City(String name, long citizens) {
        this.Name = name;
        this.Citizens = citizens;
    }

    public City(String name) {
        this.Name = name;
    }
}
