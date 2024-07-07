import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HillClimbing {
    public static List<String> hillClimbing(double[][] dislikeMatrix, List<String> people, int numIterations, int numRestarts) {
        List<String> bestSolution = null;
        double bestCost = Double.MAX_VALUE;
        Random random = new Random();

        for (int restart = 0; restart < numRestarts; restart++) {
            List<String> currentSolution = new ArrayList<>(people);
            Collections.shuffle(currentSolution);

            double currentCost = CostCalculator.calculateCost(currentSolution, dislikeMatrix, people);

            for (int iteration = 0; iteration < numIterations; iteration++) {
                int i = random.nextInt(currentSolution.size());
                int j = random.nextInt(currentSolution.size());

                Collections.swap(currentSolution, i, j);
                double newCost = CostCalculator.calculateCost(currentSolution, dislikeMatrix, people);

                if (newCost < currentCost) {
                    currentCost = newCost;
                } else {
                    Collections.swap(currentSolution, i, j);  // revert swap
                }
            }

            if (currentCost < bestCost) {
                bestCost = currentCost;
                bestSolution = new ArrayList<>(currentSolution);
            }
        }

        return bestSolution;
    }
}
