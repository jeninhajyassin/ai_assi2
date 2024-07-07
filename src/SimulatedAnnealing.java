import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    public static List<String> simulatedAnnealing(double[][] dislikeMatrix, List<String> people, double initialTemp, double coolingRate, int numIterations) {
        List<String> currentSolution = new ArrayList<>(people);
        Collections.shuffle(currentSolution);

        double currentCost = CostCalculator.calculateCost(currentSolution, dislikeMatrix, people);
        double temperature = initialTemp;
        Random random = new Random();

        for (int iteration = 0; iteration < numIterations; iteration++) {
            int i = random.nextInt(currentSolution.size());
            int j = random.nextInt(currentSolution.size());

            Collections.swap(currentSolution, i, j);
            double newCost = CostCalculator.calculateCost(currentSolution, dislikeMatrix, people);

            double delta = newCost - currentCost;
            if (delta < 0 || Math.exp(-delta / temperature) > random.nextDouble()) {
                currentCost = newCost;
            } else {
                Collections.swap(currentSolution, i, j);  // revert swap
            }

            temperature *= coolingRate;
        }

        return currentSolution;
    }
}
