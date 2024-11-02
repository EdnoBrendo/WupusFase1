package main.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.game.map.Map;
import main.game.map.Point;
import main.strategies.Strategy;

public class Player {
	public static final String CHARACTER = "W";
	private Strategy strategy;

	public Player(Strategy strategy) {
		this.strategy = strategy;
	}

	public Point evaluatePossibleNextStep(Map map) {
		Point robotLocation = map.getRobotLocation();
		List<Point> possibleNextSteps = new ArrayList<>();
		possibleNextSteps.add(new Point(robotLocation.getPositionX(),
				robotLocation.getPositionY() + 1));
		possibleNextSteps.add(new Point(robotLocation.getPositionX() + 1,
				robotLocation.getPositionY()));
		possibleNextSteps.add(new Point(robotLocation.getPositionX() - 1,
				robotLocation.getPositionY()));
		possibleNextSteps.add(new Point(robotLocation.getPositionX(),
				robotLocation.getPositionY() - 1));

		List<Point> result = new LinkedList<>();
		int[] scenarioSize = map.getScenarioSize();

		for (Point p : possibleNextSteps) {
			if (p.getPositionX() >= 0 && p.getPositionY() >= 0
					&& p.getPositionX() < scenarioSize[0]
					&& p.getPositionY() < scenarioSize[1]) {

				if (!map.isObstacle(p)) {
					result.add(p);
				}
			}
		}
		return this.strategy.evaluatePossibleNextStep(result, map);
	}
}
