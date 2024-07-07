import java.util.List;

public class CostCalculator {
    public static double calculateCost(List<String> arrangement, double[][] dislikeMatrix, List<String> people) {
        double totalCost = 0;
        int n = arrangement.size();

        for (int i = 0; i < n; i++) {
            String currentPerson = arrangement.get(i);
            String leftPerson = arrangement.get((i - 1 + n) % n);
            String rightPerson = arrangement.get((i + 1) % n);

            int currentIndex = people.indexOf(currentPerson);
            int leftIndex = people.indexOf(leftPerson);
            int rightIndex = people.indexOf(rightPerson);

            totalCost += dislikeMatrix[currentIndex][leftIndex] + dislikeMatrix[currentIndex][rightIndex];
        }

        return totalCost;
    }
}
