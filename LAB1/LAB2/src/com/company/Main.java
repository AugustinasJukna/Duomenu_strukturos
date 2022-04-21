package com.company;

import javafx.util.Pair;
import models.City;
import models.Route;
import utils.LinkList;
import utils.inOut;
import utils.Utils;

import java.io.FileNotFoundException;

import static utils.Utils.fillRoutesWithCities;
import static utils.Utils.findRoutes;

public class Main {
public static final String FirstFile = "src/resources/U8a.txt";
public static final String SecondFile = "src/resources/U8b.txt";

    public static void main(String[] args) {
        LinkList<Route> allRoutes = null;
        LinkList<City> allCities = null;
        try {
            Pair<LinkList<Route>, Integer> pair = inOut.readRoutes(FirstFile);
            allCities = inOut.readCities(SecondFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fillRoutesWithCities(allRoutes, allCities);
        long distance = 100;

        LinkList<Route> filtered = findRoutes(allRoutes, allCities, "Kaunas", distance);
        for (Route route : filtered) {
            System.out.println(route.FirstCity.Name + " " + route.SecondCity.Name);
        }
    }
}
