package utils;

import javafx.util.Pair;
import models.City;
import models.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class inOut {
    public static Pair<LinkList<Route>, Integer> readRoutes(String fileName) {
        LinkList<Route> allRoutes = new LinkList<>();
        File file = new File(fileName);
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String firstLine = reader.nextLine();
        String[] Parts = firstLine.split(" ");
        int n = Integer.parseInt(Parts[0].substring(1));
        int maxCitizens = parseInt(Parts[1]);
        int i = 0;
        while (reader.hasNextLine() && i < n) {
                String data = reader.nextLine();
                Parts = data.split(";");
                Route newRoute = new Route(new City(Parts[0]), new City(Parts[1]), parseInt(Parts[2]));
                allRoutes.add(newRoute);
                i++;
            }
            reader.close();
            return new Pair<LinkList<Route>, Integer>(allRoutes, maxCitizens);
    }

    public static LinkList<City> readCities(String fileName) throws FileNotFoundException{
        LinkList<City> allCities = new LinkList<>();
        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] parts = data.split(";");
            City city = new City(parts[0], Long.parseLong(parts[1]));
            allCities.add(city);
        }
        return allCities;
    }
}
