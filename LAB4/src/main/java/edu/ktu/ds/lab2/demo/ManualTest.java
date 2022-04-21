package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/*
 * Aibės testavimas be Gui
 * Dirbant su Intellij ir norint konsoleje matyti gražų pasuktą medį,
 * reikia FIle -> Settings -> Editor -> File Encodings -> Global encoding pakeisti į UTF-8
 *
 */
public class ManualTest {

    static Car[] cars;
    static ParsableSortedSet<Car> cSeries = new ParsableBstSet<>(Car::new, Car.byPrice);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
//        executeTest();
//        iteratorTest();
//        treeSetsTests();
//        avlTreeTest();
          RBTreesTest();
    }

    public static void iteratorTest() {
        Set<Integer> set = new BstSet<>();
        set.add(6);
        set.add(4);
        set.add(5);
        set.add(3);
        set.add(10);
        set.add(12);
        Ks.oun("Prieš naikinimus:");
        Ks.oun(set.toVisualizedString(""));
        Iterator<Integer> iterator = set.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            int item = iterator.next();
            if (item == 4 || item == 10) {
                iterator.remove();
            }
        }
        Ks.oun("Po naikinimų: ");
        Ks.oun(set.toVisualizedString(""));
    }

    public static void treeSetsTests() {
        SortedSet<Integer> set = new BstSet<>();
        set.add(6);
        set.add(4);
        set.add(5);
        set.add(3);
        set.add(10);
        set.add(12);
        Ks.oun("");
        Ks.oun(set.toVisualizedString(""));
        Set<Integer> headSet = set.headSet(10);
        Set<Integer> tailSet = set.tailSet(4);
        Set<Integer> subSet = set.subSet(4, 10);
        Ks.oun("Head Set:");
        Ks.oun(headSet.toVisualizedString(""));
        Ks.oun("Tail Set:");
        Ks.oun(tailSet.toVisualizedString(""));
        Ks.oun("Sub Set:");
        Ks.oun(subSet.toVisualizedString(""));

    }

    public static void avlTreeTest() {
        AvlSet<Integer> set = new AvlSet<>();
        set.add(6);
        set.add(4);
        set.add(5);
        set.add(3);
        set.add(10);
        set.add(12);
        Ks.oun("Prieš naikinimus (AVL tree):");
        Ks.oun(set.toVisualizedString(""));
        set.remove(5);
        set.remove(4);
        set.remove(10);
        Ks.oun("Po naikinimų (AVL tree): ");
        Ks.oun(set.toVisualizedString(""));

    }

    public static void RBTreesTest() {
        RBTree<Integer> tree = new RBTree<>();
        tree.insert(4);
        tree.insert(1);
        tree.insert(6);
        tree.insert(8);
        tree.insert(7);
        tree.insert(10);
        tree.insert(9);
        tree.insert(2);
        tree.insert(3);
        tree.insert(11);

        System.out.println("Pilnas raudonas-juodas medis:");
        System.out.println(tree.toVisualizedString(""));

        tree.remove(7);
        tree.remove(4);
        tree.remove(8);


        System.out.println("Po šalinimų:");
        System.out.println(tree.toVisualizedString(""));

        System.out.println("Get(6): " + tree.get(6));
        System.out.println("Get(2): " + tree.get(2));

//        RBTree<Car> tree1 = new RBTree<>();
//        ParsableSortedSet<Car> array = generateSet(100000, 100000);
//        for (Car car : array) {
//            tree1.insert(car);
//        }
//
//        for (Car car : array) {
//            tree1.remove(car);
//        }
//
//        System.out.println(tree1.toVisualizedString(""));


    }

    public static void executeTest() throws CloneNotSupportedException {
        Car c1 = new Car("Renault", "Laguna", 2007, 50000, 1700);
        Car c2 = new Car.Builder()
                .make("Renault")
                .model("Megane")
                .year(2011)
                .mileage(20000)
                .price(3500)
                .build();
        Car c3 = new Car.Builder().buildRandom();
        Car c4 = new Car("Renault Laguna 2011 115900 700");
        Car c5 = new Car("Renault Megane 1946 365100 9500");
        Car c6 = new Car("Honda   Civic  2011  36400 80.3");
        Car c7 = new Car("Renault Laguna 2011 115900 7500");
        Car c8 = new Car("Renault Megane 1946 365100 950");
        Car c9 = new Car("Honda   Civic  2017  36400 850.3");

        Car[] carsArray = {c9, c7, c8, c5, c1, c6};

        Ks.oun("Auto Aibė:");
        ParsableSortedSet<Car> carsSet = new ParsableBstSet<>(Car::new);


        for (Car c : carsArray) {
            carsSet.add(c);
            Ks.oun("Aibė papildoma: " + c + ". Jos dydis: " + carsSet.size());
        }
        Ks.oun("");
        Ks.oun(carsSet.toVisualizedString(""));
        Ks.oun("Po naikinimo: ");
        carsSet.remove(c5);
        carsSet.remove(c9);
        Ks.oun(carsSet.toVisualizedString(""));



        ParsableSortedSet<Car> carsSetCopy = (ParsableSortedSet<Car>) carsSet.clone();

        carsSetCopy.add(c2);
        carsSetCopy.add(c3);
        carsSetCopy.add(c4);
        Ks.oun("Papildyta autoaibės kopija:");
        Ks.oun(carsSetCopy.toVisualizedString(""));

        c9.setMileage(10000);

        Ks.oun("Originalas:");
        Ks.ounn(carsSet.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Car c : carsArray) {
            Ks.oun(c + ": " + carsSet.contains(c));
        }
        Ks.oun(c2 + ": " + carsSet.contains(c2));
        Ks.oun(c3 + ": " + carsSet.contains(c3));
        Ks.oun(c4 + ": " + carsSet.contains(c4));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Car c : carsArray) {
            Ks.oun(c + ": " + carsSetCopy.contains(c));
        }
        Ks.oun(c2 + ": " + carsSetCopy.contains(c2));
        Ks.oun(c3 + ": " + carsSetCopy.contains(c3));
        Ks.oun(c4 + ": " + carsSetCopy.contains(c4));
        Ks.oun("");

        Ks.oun("Automobilių aibė su iteratoriumi:");
        Ks.oun("");
        for (Car c : carsSet) {
            Ks.oun(c);
        }
        Ks.oun("");
        Ks.oun("Automobilių aibė AVL-medyje:");
        ParsableSortedSet<Car> carsSetAvl = new ParsableAvlSet<>(Car::new);
        for (Car c : carsArray) {
            carsSetAvl.add(c);
        }
        Ks.ounn(carsSetAvl.toVisualizedString(""));

        Ks.oun("Automobilių aibė su iteratoriumi:");
        Ks.oun("");
        for (Car c : carsSetAvl) {
            Ks.oun(c);
        }

        Ks.oun("");
        Ks.oun("Automobilių aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator<Car> iter = carsSetAvl.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Automobilių aibės toString() metodas:");
        Ks.ounn(carsSetAvl);

        // Išvalome ir suformuojame aibes skaitydami iš failo
        carsSet.clear();
        carsSetAvl.clear();

        Ks.oun("");
        Ks.oun("Automobilių aibė DP-medyje:");
        carsSet.load("data\\ban.txt");
        Ks.ounn(carsSet.toVisualizedString(""));
        Ks.oun("Išsiaiškinkite, kodėl medis augo tik į vieną pusę.");

        Ks.oun("");
        Ks.oun("Automobilių aibė AVL-medyje:");
        carsSetAvl.load("data\\ban.txt");
        Ks.ounn(carsSetAvl.toVisualizedString(""));

        Set<String> carsSet4 = CarMarket.duplicateCarMakes(carsArray);
        Ks.oun("Pasikartojančios automobilių markės:\n" + carsSet4);

        Set<String> carsSet5 = CarMarket.uniqueCarModels(carsArray);
        Ks.oun("Unikalūs automobilių modeliai:\n" + carsSet5);
    }

    static ParsableSortedSet<Car> generateSet(int kiekis, int generN) {
        cars = new Car[generN];
        for (int i = 0; i < generN; i++) {
            cars[i] = new Car.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(cars));

        cSeries.clear();
        Arrays.stream(cars).limit(kiekis).forEach(cSeries::add);
        return cSeries;
    }
}
