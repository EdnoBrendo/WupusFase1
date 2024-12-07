package main.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.game.Player;
import main.game.map.BinaryTree.Node;

public class Map {
    private static final int[] ROCK_POSITIONS_X = {0, 1, 2, 3, 4, 5, 6};
    private static final int[] ROCK_POSITIONS_Y = {0, 1, 2, 3, 4, 5, 6};

    private String[][] scenario;
    private Point robotLocation;
    private HashMap<String, Point> treasureChests;
    private BinaryTree binaryTree;  // Adicionado para armazenar a árvore binária

    public Map(int scenarioSizeX, int scenarioSizeY) {
        this.treasureChests = new HashMap<>();
        this.scenario = new String[scenarioSizeX][scenarioSizeY];
        this.robotLocation = new Point(0, 0);
        this.binaryTree = new BinaryTree(); // Instanciando a árvore binária
        this.generateMap();
    }

    private void generateMap() {
        this.scenario[this.robotLocation.getPositionX()][this.robotLocation.getPositionY()] = Player.CHARACTER;
        generateRocks();
        generateTreasureChests();
        generateMapOfTreasure();
        generateMonsters();
        
        // Criando a árvore binária a partir da matriz
        createBinaryTreeFromMap();
    }

    private void createBinaryTreeFromMap() {
        BinaryTree.Node[][] nodeMatrix = new BinaryTree.Node[scenario.length][scenario[0].length];
        
        // Criando um nó para cada célula da matriz
        for (int i = 0; i < scenario.length; i++) {
            for (int j = 0; j < scenario[i].length; j++) {
                nodeMatrix[i][j] = new BinaryTree.Node(scenario[i][j]);
            }
        }

        // Agora conectando os nós (preenchendo a árvore binária com filhos)
        for (int i = 0; i < scenario.length; i++) {
            for (int j = 0; j < scenario[i].length; j++) {
                Node currentNode = nodeMatrix[i][j];

                // Filho da direita
                if (j + 1 < scenario[i].length) {
                    Node rightChild = nodeMatrix[i][j + 1];
                    currentNode.setRight(rightChild);
                }

                // Filho de baixo
                if (i + 1 < scenario.length) {
                    Node bottomChild = nodeMatrix[i + 1][j];
                    currentNode.setLeft(bottomChild);
                }
            }
        }

        // O primeiro nó será a raiz da árvore
        this.binaryTree.setRoot(nodeMatrix[0][0]);
    }

    public BinaryTree getBinaryTree() {
        return this.binaryTree;
    }

    public void printTree() {
        // Chamando DFS para imprimir a árvore em pré-ordem
        System.out.println("Busca em Profundidade:");
        this.binaryTree.depthFirstSearch(this.binaryTree.getRoot());
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
            int treasureChestsX = random.nextInt(this.scenario.length);
            int treasureChestsY;
            if (treasureChestsX == (this.scenario[0].length - 1)) {
                treasureChestsY = random.nextInt(this.scenario[0].length);
            } else {
                treasureChestsY = this.scenario.length - 1;
            }

            if (scenario[treasureChestsX][treasureChestsY] == null) {
                scenario[treasureChestsX][treasureChestsY] = TreasureChest.CHARACTER;

                int index = random.nextInt(treasureCharacters.size());
                treasureChests.put(treasureCharacters.get(index), new Point(treasureChestsX, treasureChestsY));
                treasureCharacters.remove(index);
                treasureChestCount++;
            }
        }
    }

    private void generateMonsters() {
        Random random = new Random();
        List<Monster> monsters = new ArrayList<>();
        int monsterCount = 0;
        while (monsterCount < 3) {
            int monsterRandomX = random.nextInt(this.scenario.length - 3) + 2;
            int monsterRandomY = random.nextInt(this.scenario[0].length - 3) + 2;

            if (this.scenario[monsterRandomX][monsterRandomY] == null) {
                this.scenario[monsterRandomX][monsterRandomY] = Monster.CHARACTER;
                monsterCount++;
            }
        }

        for (int i = 0; i < monsters.size(); i++) {
            Point coordinate = monsters.get(i).getPoints();
            this.scenario[coordinate.getPositionX()][coordinate.getPositionY()] = Monster.CHARACTER;
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
            int positionY1 = ROCK_POSITIONS_X[indexRandomY];

            int positionX2 = ROCK_POSITIONS_X[indexRandomX];
            int positionY2 = ROCK_POSITIONS_X[indexRandomY] + 1;

            int positionX3 = ROCK_POSITIONS_X[indexRandomX] + 1;
            int positionY3 = ROCK_POSITIONS_X[indexRandomY];

            int positionX4 = ROCK_POSITIONS_X[indexRandomX] + 1;
            int positionY4 = ROCK_POSITIONS_X[indexRandomY] + 1;

            List<Point> rockPoints = new LinkedList<>();
            rockPoints.add(new Point(positionX1, positionY1));
            rockPoints.add(new Point(positionX2, positionY2));
            rockPoints.add(new Point(positionX3, positionY3));
            rockPoints.add(new Point(positionX4, positionY4));

            if (!rocks.isEmpty()) {
                boolean conflict = false;

                for (int i = 0; i < rocks.size(); i++) {
                    Rock c = rocks.get(i);
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

        for (int i = 0; i < rocks.size(); i++) {
            List<Point> points = rocks.get(i).getPoints();
            for (int j = 0; j < points.size(); j++) {
                Point point = points.get(j);
                this.scenario[point.getPositionX()][point.getPositionY()] = Rock.CHARACTER;
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
		return this.scenario[point.getPositionX()][point.getPositionY()];
	}

	public void moveRobot(Point nextPoint) {
		this.scenario[nextPoint.getPositionX()][nextPoint.getPositionY()] = Player.CHARACTER;
		this.scenario[this.robotLocation.getPositionX()][this.robotLocation.getPositionY()] = "*";
		this.robotLocation = nextPoint;
	}

	public void openTreasureChest(Point nextPoint) {
		Iterator<String> it = treasureChests.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if (treasureChests.get(key).equals(nextPoint)) {
				if(key.equals(TreasureChest.CHEST_TRESURE_CHARACTER)) {
					System.out.println("Parabéns voçê encontrou o tesouro!");
				} else if (key.equals(TreasureChest.CHEST_TRAP_CHARACTER)) {
					System.out.println("O jogo acabou! Voçê morreu, caiu em uma armadilha, seu noob.");
				} else if (key.equals(TreasureChest.CHEST_EMPTY_CHARACTER)) {
					System.out.println("Aqui não tem nada, seu trouxa!");
				}
				this.scenario[nextPoint.getPositionX()][nextPoint.getPositionY()] = key;
				break;
			}
		}
	}

	public int[] getScenarioSize() {
		int[] size = {this.scenario.length, this.scenario[0].length};
		return size;
	}
}