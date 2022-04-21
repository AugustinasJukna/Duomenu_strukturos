package utils;
import models.City;
import models.Route;
import sun.awt.image.ImageWatched;


public class Utils {
    public static LinkList<Route> findRoutes(LinkList<Route> allRoutes, LinkList<City> allCities, String firstCity, long distance) {
        LinkList<Route> filteredRoutes = new LinkList<>();
        for(Route route: allRoutes) {
            if (route.FirstCity.Name == firstCity && route.Distance > distance && compareCitizens(route.SecondCity, distance, allCities)
                    || findConnection(route, firstCity, allRoutes) && compareCitizens(route.SecondCity, distance, allCities)) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    public static void fillRoutesWithCities(LinkList<Route> allRoutes, LinkList<City> allCities){
            for (Route route : allRoutes) {
                for (City city : allCities) {
                    if (route.FirstCity.Name == city.Name) {
                        route.FirstCity.Citizens = city.Citizens;
                    }
                    else if (route.SecondCity.Name == city.Name) {
                        route.SecondCity.Citizens = city.Citizens;
                    }
                }
        }
    }

    public static boolean findConnection(Route a, String starting, LinkList<Route> allRoutes) {
        for (Route route : allRoutes) {
            if (route.FirstCity.Name == starting && route.SecondCity == a.FirstCity)
                return true;
        }
        return false;
    }

    public static boolean compareCitizens(City a, long citizens, LinkList<City> allCities) {
        for (City city : allCities) {
            if (city.Name == a.Name) {
                if (city.Citizens < citizens)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
}
