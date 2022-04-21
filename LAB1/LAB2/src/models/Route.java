package models;

public class Route {
    public City FirstCity;
    public City SecondCity;
    public int Distance;

    public Route(City cityA, City cityB, int distance) {
    this.FirstCity = cityA;
    this.SecondCity = cityB;
    this.Distance = distance;
    }
}
