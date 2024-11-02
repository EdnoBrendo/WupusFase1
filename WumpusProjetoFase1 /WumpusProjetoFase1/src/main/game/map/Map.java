package main.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.game.Player;

public class Map {
	private static final int[] ROCK_POSITIONS_X = { 0, 1, 2, 3, 4, 5, 6 };
	private static final int[] ROCK_POSITIONS_Y = { 0, 1, 2, 3, 4, 5, 6 };

	private String[][] scenario;
	private Point robotLocation;

	private HashMap<String, Point> treasureChests;

	public Map(int scenarioSizeX, int scenarioSizeY) {
		this.treasureChests = new HashMap<>();
		this.scenario = new String[scenarioSizeX][scenarioSizeY];
		this.robotLocation = new Point(0, 0);
		this.generateMap();
	}

	public int[] getScenarioSize() {
		return new int[] { scenario.length, scenario[0].length };
	}

	public List<Point> getTreasures() {
		List<Point> treasurePoints = new ArrayList<>();
		for (Point point : treasureChests.values()) {
			treasurePoints.add(point);
		}
		return treasurePoints;
	}

	public Point getTreasureLocation(String chestType) {
		return treasureChests.get(chestType);
	}

	public Point getTreasureLocation() {
		if (!treasureChests.isEmpty()) {
			return treasureChests.values().iterator().next();
		}
		return null;
	}

	private String[][] generateMap() {
		this.scenario[this.robotLocation.getPositionX()][this.robotLocation
				.getPositionY()] = Player.CHARACTER;
		generateRocks();
		generateTreasureChests();
		generateMapOfTreasure();
		generateMonsters();

		return scenario;
	}

	private void generateMapOfTreasure() {
		Random random = new Random();
		int mapOfTreasureCount = 0;
		while (mapOfTreasureCount < 1) {
			int mapRandomX = random.nextInt(this.scenario.length - 2) + 2;
			int mapRandomY = random.nextInt(this.scenario[0].length - 2) + 2;
			if (scenario[mapRandomX][mapRandomY] == null) {
				this.scenario[mapRandomX][mapRandomY] = MapOfTreasure.CHARACTER;
				mapOfTreasureCount++;
			}
		}
	}

	private void generateTreasureChests() {
		Random random = new Random();
		int treasureChestCount = 0;
		List<String> treasureCharacters = new LinkedList<>();
		treasureCharacters.add(TreasureChest.CHEST_EMPTY_CHARACTER);
		treasureCharacters.add(TreasureChest.CHEST_TRAP_CHARACTER);
		treasureCharacters.add(TreasureChest.CHEST_TRESURE_CHARACTER);

		while (treasureChestCount < 3) {
			int treasureChestsX = random.nextInt(this.scenario.length - 2) + 1;
			int treasureChestsY = random.nextInt(this.scenario[0].length - 2) + 1;

			if (isPointValid(new Point(treasureChestsX, treasureChestsY))
					&& !isNearOtherChests(treasureChestsX, treasureChestsY)) {
				scenario[treasureChestsX][treasureChestsY] = TreasureChest.CHARACTER;

				int index = random.nextInt(treasureCharacters.size());
				treasureChests.put(treasureCharacters.get(index), new Point(
						treasureChestsX, treasureChestsY));
				treasureCharacters.remove(index);
				treasureChestCount++;
			}
		}
	}

	// Método para verificar se o ponto está próximo de outros baús
	private boolean isNearOtherChests(int x, int y) {
		Point newPoint = new Point(x, y); // Cria um novo ponto para a posição
											// do baú
		for (Point point : treasureChests.values()) {
			if (newPoint.distance(point) <= 2.0) { // Verifica a distância
				return true; // Há outro baú próximo
			}
		}
		return false; // Não há baús próximos
	}

	private void generateMonsters() {
		Random random = new Random();
		int monsterCount = 0;
		while (monsterCount < 3) {
			int monsterRandomX = random.nextInt(this.scenario.length - 3) + 2;
			int monsterRandomY = random.nextInt(this.scenario[0].length - 3) + 2;

			if (isPointValid(new Point(monsterRandomX, monsterRandomY))) {
				this.scenario[monsterRandomX][monsterRandomY] = Monster.CHARACTER;
				monsterCount++;
			}
		}
	}

	private void generateRocks() {
		Random random = new Random();

		List<Rock> rocks = new ArrayList<>();
		int rockCount = 0;
		while (rockCount < 3) {
			int indexRandomX = random.nextInt(ROCK_POSITIONS_X.length);
			int indexRandomY;
			if (indexRandomX < 2) {
				indexRandomY = random.nextInt(ROCK_POSITIONS_Y.length - 2) + 2;
			} else {
				indexRandomY = random.nextInt(ROCK_POSITIONS_Y.length);
			}

			int positionX1 = ROCK_POSITIONS_X[indexRandomX];
			int positionY1 = ROCK_POSITIONS_Y[indexRandomY];

			int positionX2 = ROCK_POSITIONS_X[indexRandomX];
			int positionY2 = ROCK_POSITIONS_Y[indexRandomY] + 1;

			int positionX3 = ROCK_POSITIONS_X[indexRandomX] + 1;
			int positionY3 = ROCK_POSITIONS_Y[indexRandomY];

			int positionX4 = ROCK_POSITIONS_X[indexRandomX] + 1;
			int positionY4 = ROCK_POSITIONS_Y[indexRandomY] + 1;

			List<Point> rockPoints = new LinkedList<>();
			rockPoints.add(new Point(positionX1, positionY1));
			rockPoints.add(new Point(positionX2, positionY2));
			rockPoints.add(new Point(positionX3, positionY3));
			rockPoints.add(new Point(positionX4, positionY4));

			if (!rocks.isEmpty()) {
				boolean conflict = false;

				for (Rock c : rocks) {
					if (c.hasConflict(rockPoints)) {
						conflict = true;
						break;
					}
				}
				if (conflict) {
					continue;
				}
			}

			rocks.add(new Rock(rockPoints));
			rockCount++;
		}

		for (Rock rock : rocks) {
			List<Point> points = rock.getPoints();
			for (Point point : points) {
				if (isPointValid(point)) {
					this.scenario[point.getPositionX()][point.getPositionY()] = Rock.CHARACTER;
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < scenario.length; i++) {
			for (int j = 0; j < scenario[i].length; j++) {
				if (scenario[i][j] == null) {
					scenario[i][j] = "*";
				}
				if (j == (scenario[i].length - 1)) {
					System.out.println(scenario[i][j]);
				} else {
					System.out.print(scenario[i][j] + " ");
				}
			}
		}
	}

	public Point getRobotLocation() {
		return this.robotLocation;
	}

	public String get(Point point) {
		if (isPointValid(point)) {
			return this.scenario[point.getPositionX()][point.getPositionY()];
		}
		return null;
	}

	public void moveRobot(Point nextPoint) {
		if (isPointValid(nextPoint)) {
			this.scenario[nextPoint.getPositionX()][nextPoint.getPositionY()] = Player.CHARACTER;
			this.scenario[this.robotLocation.getPositionX()][this.robotLocation
					.getPositionY()] = "*";
			this.robotLocation = nextPoint;
		}
	}

	public void openTreasureChest(Point nextPoint) {
		Iterator<String> it = treasureChests.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Point treasureLocation = treasureChests.get(key);
			if (treasureLocation != null && treasureLocation.equals(nextPoint)) {
				if (key.equals(TreasureChest.CHEST_TRESURE_CHARACTER)) {
					System.out.println("Parabéns, você encontrou o tesouro!");
				} else if (key.equals(TreasureChest.CHEST_TRAP_CHARACTER)) {
					System.out
							.println("O jogo acabou! Você morreu, caiu em uma armadilha, seu noob!");
				} else {
					System.out.println("Aqui não tem nada, seu trouxa!");
				}
				this.scenario[nextPoint.getPositionX()][nextPoint
						.getPositionY()] = key;
				break;
			}
		}
	}

	public boolean containsPoint(Point point) {
		return isPointValid(point);
	}

	public boolean isPointValid(Point point) {
		int x = point.getPositionX();
		int y = point.getPositionY();
		return x >= 0 && x < scenario.length && y >= 0
				&& y < scenario[x].length;
	}

	public boolean isObstacle(Point point) {
		String space = this.scenario[point.getPositionX()][point.getPositionY()];
		return space != null
				&& (space.equals(Rock.CHARACTER) || space
						.equals(Monster.CHARACTER));
	}
}