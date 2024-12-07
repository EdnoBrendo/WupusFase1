package main.game;

import main.game.map.Map;
import main.game.map.MapOfTreasure;
import main.game.map.Point;
import main.game.map.TreasureChest;
import main.strategies.RandomStrategy;

public class Game {
	private Map map;
	private Player player;
	private static final int MAX_MOVES = 100;

	public Game() {
		this.map = new Map(8, 8);
		this.player = new Player(new RandomStrategy());
	}

	public void run() {
		this.map.print();
		System.out.println();

		int moves = 0;
		boolean mapOpened = false;

		while (true) {
			if (moves >= MAX_MOVES) {
				System.out
						.println("O jogo terminou! Número máximo de movimentos atingido.");
				break;
			}

			Point nextPoint = this.player.evaluatePossbileNextStep(map);
			if (nextPoint == null) {
				break;
			} else {
				String space = this.map.get(nextPoint);

				if (space != null && space.equals(MapOfTreasure.CHARACTER)
						&& !mapOpened) {

					System.out
							.println("O robô encontrou o mapa do tesouro e agora sabe onde o baú está localizado!");
					mapOpened = true;

				} else if (space != null
						&& space.equals(TreasureChest.CHARACTER)) {
					this.map.openTreasureChest(nextPoint);
					this.map.print();
					break;
				} else {
					this.map.moveRobot(nextPoint);
				}
			}

			this.map.print();
			System.out.println();
			moves++;
		}
	}
}