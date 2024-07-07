import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.util.Pair;

public class GeneticAlgorithm {
    private static List<List<String>> createInitialPopulation(int size, List<String> people) {
        List<List<String>> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<String> individual = new ArrayList<>(people);
            Collections.shuffle(individual);
            population.add(individual);
        }
        return population;
    }

    private static List<List<String>> selection(List<List<String>> population, List<Double> costs, double retainFraction) {
        List<Pair<Double, List<String>>> paired = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            paired.add(new Pair<>(costs.get(i), population.get(i)));
        }

        Collections.sort(paired, (a, b) -> Double.compare(a.getKey(), b.getKey()));
        int retainedNum = (int) (population.size() * retainFraction);
        List<List<String>> selected = new ArrayList<>();
        for (int i = 0; i < retainedNum; i++) {
            selected.add(paired.get(i).getValue());
        }
        return selected;
    }

    private static Pair<List<String>, List<String>> crossover(List<String> parent1, List<String> parent2) {
        int point = new Random().nextInt(parent1.size());
        List<String> child1 = new ArrayList<>(parent1.subList(0, point));
        List<String> child2 = new ArrayList<>(parent2.subList(0, point));

        for (String p : parent2) {
            if (!child1.contains(p)) {
                child1.add(p);
            }
        }

        for (String p : parent1) {
            if (!child2.contains(p)) {
                child2.add(p);
            }
        }

        return new Pair<>(child1, child2);
    }

    private static List<String> mutate(List<String> individual, double mutationRate) {
        Random random = new Random();
        if (random.nextDouble() < mutationRate) {
            int i = random.nextInt(individual.size());
            int j = random.nextInt(individual.size());
            Collections.swap(individual, i, j);
        }
        return individual;
    }

    public static List<String> geneticAlgorithm(double[][] dislikeMatrix, List<String> people, int populationSize, int numGenerations, double mutationRate) {
        List<List<String>> population = createInitialPopulation(populationSize, people);
        Random random = new Random();

        for (int generation = 0; generation < numGenerations; generation++) {
            List<Double> costs = new ArrayList<>();
            for (List<String> individual : population) {
                costs.add(CostCalculator.calculateCost(individual, dislikeMatrix, people));
            }

            population = selection(population, costs, 0.6);

            List<List<String>> children = new ArrayList<>();
            while (children.size() < populationSize - population.size()) {
                List<String> parent1 = population.get(random.nextInt(population.size()));
                List<String> parent2 = population.get(random.nextInt(population.size()));
                Pair<List<String>, List<String>> offspring = crossover(parent1, parent2);
                children.add(offspring.getKey());
                children.add(offspring.getValue());
            }

            for (List<String> child : children) {
                population.add(mutate(child, mutationRate));
            }
        }

        List<Double> finalCosts = new ArrayList<>();
        for (List<String> individual : population) {
            finalCosts.add(CostCalculator.calculateCost(individual, dislikeMatrix, people));
        }

        double bestCost = Collections.min(finalCosts);
        int bestIndex = finalCosts.indexOf(bestCost);
        return population.get(bestIndex);
    }
}
