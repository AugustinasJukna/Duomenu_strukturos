package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sun.util.resources.cldr.sr.CalendarData_sr_Latn_RS;

import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class Benchmark {

    @State(Scope.Benchmark)
    public static class FullSet {

        Car[] cars;
        AvlSet<Car> avlSet;
        RBTree<Car> rbTree;

        @Setup(Level.Iteration)
        public void generateElements(BenchmarkParams params) {
            cars = Benchmark.generateElements(Integer.parseInt(params.getParam("elementCount")));
        }

        @Setup(Level.Invocation)
        public void fillBstSet(BenchmarkParams params) {
            avlSet = new AvlSet<>();
            addElements(cars, avlSet);
        }

        @Setup(Level.Invocation)
        public void fillTreeSet(BenchmarkParams params) {
            rbTree = new RBTree<>();
            addElements(cars, rbTree);
        }
    }

    @Param({"10000", "20000", "40000", "80000", "160000"})
    public int elementCount;

    Car[] cars;

    @Setup(Level.Iteration)
    public void generateElements() {
        cars = generateElements(elementCount);
    }

    static Car[] generateElements(int count) {
        return new CarsGenerator().generateShuffle(count, 1.0);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void getBenchmarkAVLTree(FullSet benchmark) {
        for (Car car : benchmark.cars) {
            benchmark.avlSet.get(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void getBenchmarkRBTree(FullSet benchmark) {
        for (Car car : benchmark.cars) {
            benchmark.rbTree.get(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void insertBenchmarkRBTree(FullSet benchmark) {
        RBTree<Car> newTree = new RBTree<>();
        for (Car car : benchmark.cars) {
            newTree.insert(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void insertBenchmarkAVLTree(FullSet benchmark) {
        AvlSet<Car> newTree = new AvlSet<>();
        for (Car car : benchmark.cars) {
            newTree.add(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void removeBenchmarkAVL(FullSet benchmark) {
        for (Car car : benchmark.cars) {
            benchmark.avlSet.remove(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void removeBenchmarkRBTree(FullSet benchmark) {
        for (Car car : benchmark.cars) {
            benchmark.rbTree.remove(car);
        }
    }



    public static void addElements(Car[] carArray, SortedSet<Car> carSet) {
        for (Car car : carArray) {
            carSet.add(car);
        }
    }

    public static void addElements(Car[] carArray, RBTree<Car> rbTree) {
        for (Car car : carArray) {
            rbTree.insert(car);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
