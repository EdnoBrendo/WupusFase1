package main.strategies;

import java.util.List;
import java.util.Random;
import main.game.map.Map;
import main.game.map.Point;

public class RandomStrategy implements Strategy {
	private Random random;

	public RandomStrategy() {
		this.random = new Random();
	}

	@Override
	public Point evaluatePossibleNextStep(List<Point> possibleNextSteps, Map map) {
		if (possibleNextSteps.isEmpty()) {
			return null;
		}
		int randomIndex = random.nextInt(possibleNextSteps.size());
		return possibleNextSteps.get(randomIndex);
	}
}