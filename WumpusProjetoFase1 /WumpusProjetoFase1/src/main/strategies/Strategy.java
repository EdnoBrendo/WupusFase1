package main.strategies;

import java.util.List;
import main.game.map.Map;
import main.game.map.Point;

public interface Strategy {
	Point evaluatePossibleNextStep(List<Point> possibleNextSteps, Map map);
}
