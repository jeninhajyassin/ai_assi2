import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            double[][] dislikeMatrix = DislikeScores.readDislikeScores("dislike.xlsx");
            List<String> people = Arrays.asList("Ahmed", "Salem", "Ayman", "Hani", "Kamal", "Samir", "Hakam", "Fuad", "Ibrahim","Khalid");

            List<String> bestArrangement = HillClimbing.hillClimbing(dislikeMatrix, people, 1000, 100);
            double bestCost = CostCalculator.calculateCost(bestArrangement, dislikeMatrix, people);
            System.out.println("\nHill Climbing - Best seating arrangement: " + bestArrangement);
            System.out.println("Hill Climbing - Total dislike cost: " + bestCost);

            bestArrangement = SimulatedAnnealing.simulatedAnnealing(dislikeMatrix, people, 1000, 0.99, 10000);
            bestCost = CostCalculator.calculateCost(bestArrangement, dislikeMatrix, people);
            System.out.println("\nSimulated Annealing - Best seating arrangement: " + bestArrangement);
            System.out.println("Simulated Annealing - Total dislike cost: " + bestCost);

            bestArrangement = GeneticAlgorithm.geneticAlgorithm(dislikeMatrix, people, 100, 1000, 0.1);
            bestCost = CostCalculator.calculateCost(bestArrangement, dislikeMatrix, people);
            System.out.println("\nGenetic Algorithm - Best seating arrangement: " + bestArrangement);
            System.out.println("Genetic Algorithm - Total dislike cost: " + bestCost);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
