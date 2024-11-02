package main.strategies;

import java.util.List;
import main.game.map.Map;
import main.game.map.Point;

public class MonsterCountStrategy implements Strategy {
	@Override
	public Point evaluatePossibleNextStep(List<Point> possibleNextSteps, Map map) {
		Point safestPoint = null;
		int lowestMonsterCount = Integer.MAX_VALUE;

		for (Point point : possibleNextSteps) {
			int monsterCount = countAdjacentMonsters(point, map);
			if (monsterCount < lowestMonsterCount) {
				lowestMonsterCount = monsterCount;
				safestPoint = point;
			}
		}

		return safestPoint;
	}

	private int countAdjacentMonsters(Point point, Map map) {
		int count = 0;
		int x = point.getPositionX();
		int y = point.getPositionY();

		Point[] adjacentPoints = { new Point(x, y + 1),
				new Point(x + 1, y),
				new Point(x - 1, y),
				new Point(x, y - 1)
		};

		for (Point adjacentPoint : adjacentPoints) {
			if (map.isPointValid(adjacentPoint)) {
													
				String cellValue = map.get(adjacentPoint);
				if ("M".equals(cellValue)) {
					count++;
				}
			}
		}

		return count;
	}
}