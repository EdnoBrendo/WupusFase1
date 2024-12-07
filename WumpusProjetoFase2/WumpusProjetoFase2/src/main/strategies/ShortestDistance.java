package main.strategies;

import java.util.List;
import main.game.map.Map;
import main.game.map.Point;

public class ShortestDistance implements Strategy {
	@Override
	public Point evaluatePossibleNextStep(List<Point> possibleNextSteps, Map map) {
		Point closestPoint = null;
		double closestDistance = Double.MAX_VALUE;

		Point robotLocation = map.getRobotLocation();

		for (Point point : possibleNextSteps) {
			double distance = calculateDistance(robotLocation, point);
			if (distance < closestDistance) {
				closestDistance = distance;
				closestPoint = point;
			}
		}

		return closestPoint;
	}

	private double calculateDistance(Point a, Point b) {
		return Math.sqrt(Math.pow(b.getPositionX() - a.getPositionX(), 2)
				+ Math.pow(b.getPositionY() - a.getPositionY(), 2));
	}
}