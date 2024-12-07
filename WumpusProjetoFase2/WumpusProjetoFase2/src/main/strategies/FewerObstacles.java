package main.strategies;

import main.game.map.Map;
import main.game.map.Point;

import java.util.List;

public class FewerObstacles implements Strategy {

    @Override
    public Point evaluatePossibleNextStep(List<Point> possibleNextSteps, Map map) {
        Point bestPoint = null;
        int fewestObstacles = Integer.MAX_VALUE;

        for (Point point : possibleNextSteps) {
            int obstacleCount = countObstaclesNearby(point, map);
            
            if (obstacleCount < fewestObstacles) {
                fewestObstacles = obstacleCount;
                bestPoint = point;
            }
        }
        
        return bestPoint;
    }

    private int countObstaclesNearby(Point point, Map map) {
        int count = 0;
        int[] scenarioSize = map.getScenarioSize();
        int x = point.getPositionX();
        int y = point.getPositionY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;

                if (newX >= 0 && newX < scenarioSize[0] && newY >= 0 && newY < scenarioSize[1]) {
                    String space = map.get(new Point(newX, newY));

                    if (space != null && (space.equals("M") || space.equals("R"))) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}